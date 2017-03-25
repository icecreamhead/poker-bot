package com.icecreamhead.pokerbot.model;

import com.google.inject.Inject;

import java.util.UUID;

public class TestBot implements Bot {

  private final BotConfig config;

  @Inject
  public TestBot(BotConfig config) {
    this.config = config;
  }

  @Override
  public BotRequest handleResponse(ServerResponse response) {
    if (response == null) {
      return offerGame();
    }

    switch (response.getResponseType()) {
      case OFFER_GAME_RESPONSE:
        return handleOfferGameResponse((GameStateResponse)response);
    }

    return null;
  }

  private OfferGame offerGame() {
    return new OfferGame(config.getBotId(),
        config.getBotPassword(),
        5000, 9, false, false, null);
  }

  private BotRequest handleOfferGameResponse(GameStateResponse gameStateResponse) {
    switch (gameStateResponse.getResult()) {
      case WAITING_FOR_GAME:
        return new PollForGameState(config.getBotId(), config.getBotPassword(), 5000, gameStateResponse.getPlayerKey());

      case SUCCESS:
        return makeGameDecision(gameStateResponse.getPlayerKey(), gameStateResponse.getGameState());

      default:
        throw new RuntimeException(gameStateResponse.getResult().name());
    }
  }

  private BotRequest makeGameDecision(UUID playerKey, GameState gameState) {
    return null;
  }
}
