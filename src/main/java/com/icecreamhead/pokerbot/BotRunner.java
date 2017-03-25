package com.icecreamhead.pokerbot;

import com.google.inject.Inject;
import com.google.inject.Provider;

import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.model.AbstractBotRequest;
import com.icecreamhead.pokerbot.model.Bot;
import com.icecreamhead.pokerbot.model.GameStateResponse;
import com.icecreamhead.pokerbot.model.MakeMove;
import com.icecreamhead.pokerbot.model.OfferGame;
import com.icecreamhead.pokerbot.model.PollForGameState;
import com.icecreamhead.pokerbot.model.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotRunner implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(BotRunner.class);

  private final PokerApi pokerApi;
  private final Provider<Bot> botProvider;

  @Inject
  public BotRunner(Provider<Bot> botProvider, PokerApi pokerApi) {
    this.botProvider = botProvider;
    this.pokerApi = pokerApi;
  }

  public void run() {
    logger.info("Starting!");
    // Attempt to connect!

    GameStateResponse response = null;
    Bot bot = botProvider.get();
    while (true) {
      AbstractBotRequest request = bot.handleResponse(response);

      if (request == null) {
        logger.error("No action selected! Exiting :(");
        break;
      }

      pauseForDramaticEffect();

      switch (request.getAction()) {
        case NEW_GAME:
          logger.info("Offering new game");
          response = pokerApi.offerGame((OfferGame) request);
          break;

        case POLL_FOR_GAME_STATE:
          logger.info("Polling for game state");
          response = pokerApi.pollForGameState((PollForGameState) request);
          break;

        case MAKE_MOVE:
          logger.info("Playing move: {}", ((MakeMove) request).getMove());
          response = pokerApi.makeMove((MakeMove) request);
          break;
      }

      if (response.getResult() == Result.GAME_HAS_ENDED ||
          (response.getGameState() != null && response.getGameState().getGameStatus().isEndState())) {
        logger.info("Game has ended. Result: {}\n", response.getGameState().getGameStatus());
        logger.info("Starting new game!");
        response = null;
        bot = botProvider.get();
        break;
      }
    }
  }


  private void pauseForDramaticEffect() {
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
