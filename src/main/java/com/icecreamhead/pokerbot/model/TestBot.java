package com.icecreamhead.pokerbot.model;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

import static com.icecreamhead.pokerbot.model.HandUtil.hasFaceCard;
import static com.icecreamhead.pokerbot.model.HandUtil.hasPair;
import static com.icecreamhead.pokerbot.model.HandUtil.isSuited;

public class TestBot implements Bot {

  private static final Logger logger = LoggerFactory.getLogger(TestBot.class);

  private final String botId;
  private final String botpassword;

  private UUID playerKey;

  @Inject
  public TestBot(BotConfig config) {
    this.botId = config.getBotId();
    this.botpassword = config.getBotPassword();
  }

  @Override
  public AbstractBotRequest handleResponse(ServerResponse response) {
    if (response == null) {
      return offerGame();
    }

    switch (response.getResponseType()) {
      case OFFER_GAME_RESPONSE:
        return handleGameStateResponse((GameStateResponse) response);
    }

    return null;
  }

  private OfferGame offerGame() {
    return new OfferGame(botId, botpassword,
        5000, 9, false, false, null);
  }

  private AbstractBotRequest handleGameStateResponse(GameStateResponse gameStateResponse) {
    logger.info("Handling game state: {}", gameStateResponse.getResult());
    if (gameStateResponse.getPlayerKey() != null) {
      playerKey = gameStateResponse.getPlayerKey();
    }

    switch (gameStateResponse.getResult()) {
      case NOT_YOUR_MOVE:
      case WAITING_FOR_GAME:
      case CALL_ALREADY_IN_PROCESS:
        return waitForTurn();

      case SUCCESS:
        if (gameStateResponse.getGameState().isMover()) {
          return makeGameDecision(gameStateResponse.getGameState());
        } else {
          return waitForTurn();
        }

      case INVALID_MOVE:
        return fold();

      default:
        throw new RuntimeException(gameStateResponse.getResult().name());
    }
  }

  private AbstractBotRequest waitForTurn() {
    return new PollForGameState(botId, botpassword, 5000, playerKey);
  }

  private AbstractBotRequest makeGameDecision(GameState gameState) {
    logger.info("Making decision. Hand is {}. Board is {}", gameState.getPlayerHand(), gameState.getBoardCards());
    int bet = calcBet(gameState.getPlayerHand(), gameState.getBoardCards());

    if (bet >= 0) {
      logger.info("Decided to bet {}", bet);
      return bet(bet);
    }

    logger.info("Decided to fold");
    return fold();
  }

  private int calcBet(List<Card> playerHand, List<Card> boardCards) {
    int bet = 0;

    switch (boardCards.size()) {
      case 0:
        if (hasFaceCard(playerHand)) {
          bet += 5;
        }
        if (hasPair(playerHand)) {
          bet += 5;
        }

        if (isSuited(playerHand)) {
          bet += 5;
        }
        return bet;
      default:
        return 0;
    }

//    return -1;
  }

  private MakeMove fold() {
    return new MakeMove(botId, botpassword, playerKey, new Move(true, 0));
  }

  private MakeMove bet(int bet) {
    return new MakeMove(botId, botpassword, playerKey, new Move(false, bet));
  }

}
