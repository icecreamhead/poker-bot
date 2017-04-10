package com.icecreamhead.pokerbot.model;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public abstract class AbstractBot implements Bot {

  protected final Logger logger = LoggerFactory.getLogger(getClass());

  private final String botId;
  private final String botpassword;
  private final OfferGame offerGame;

  private UUID playerKey;

  @Inject
  protected AbstractBot(BotConfig config, OfferGame offerGame) {
    this.botId = config.getBotId();
    this.botpassword = config.getBotPassword();
    this.offerGame = offerGame;
  }

  private AbstractBotRequest handleGameStateResponse(GameStateResponse gameStateResponse) {
    switch (gameStateResponse.getResult()) {
      case NOT_YOUR_MOVE:
      case WAITING_FOR_GAME:
        return waitForTurn();

      case SUCCESS:
        if (gameStateResponse.getGameState().isMover()) {
          logger.info("Your move. Board is {}", gameStateResponse.getGameState().getHand());
          AbstractBotRequest decision = makeGameDecision(gameStateResponse.getGameState());
          logger.debug("Bot decision: {}", decision);
          return decision;
        } else {
          return waitForTurn();
        }

      case INVALID_MOVE:
        return checkOrFold();

      default:
        throw new RuntimeException(gameStateResponse.getResult().name());
    }
  }

  @Override
  public AbstractBotRequest handleResponse(ServerResponse response) {
    if (response == null) {
      return offerGame;
    }

    switch (response.getResponseType()) {
      case OFFER_GAME_RESPONSE:
        GameStateResponse gameStateResponse = (GameStateResponse) response;
        logger.debug("Handling game state: {}", gameStateResponse.getResult());
        if (gameStateResponse.getPlayerKey() != null) {
          playerKey = gameStateResponse.getPlayerKey();
        }
        return handleGameStateResponse((GameStateResponse) response);
    }

    return null;
  }

  private AbstractBotRequest waitForTurn() {
    return new PollForGameState(botId, botpassword, 5000, playerKey);
  }

  protected UUID getPlayerKey() {
    return playerKey;
  }

  protected abstract AbstractBotRequest makeGameDecision(GameState gameState);

  protected MakeMove checkOrFold() {
    return new MakeMove(botId, botpassword, playerKey, new Move(true, 0));
  }

  protected MakeMove bet(int bet) {
    return new MakeMove(botId, botpassword, playerKey, new Move(false, bet));
  }
}
