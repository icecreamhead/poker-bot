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

  private int won = 0;
  private int drawn = 0;
  private int lost = 0;

  @Inject
  public BotRunner(Provider<Bot> botProvider, PokerApi pokerApi) {
    this.botProvider = botProvider;
    this.pokerApi = pokerApi;
  }

  public void run() {

    GameStateResponse response = null;
    Bot bot = botProvider.get();
    while (true) {
      try {
        AbstractBotRequest request = bot.handleResponse(response);

        if (request == null) {
          logger.error("No action selected! Exiting :(");
          break;
        }

//      pauseForDramaticEffect(1);

        switch (request.getAction()) {
          case NEW_GAME:
            logger.warn("About to start new game!");
//          pauseForDramaticEffect(1000);
            response = pokerApi.offerGame((OfferGame) request);
            break;

          case POLL_FOR_GAME_STATE:
            logger.warn("Waiting for game...");
            pauseForDramaticEffect(2000);
            response = pokerApi.pollForGameState((PollForGameState) request);
            break;

          case MAKE_MOVE:
//          logger.info("Playing move: {}", ((MakeMove) request).getMove());
            response = pokerApi.makeMove((MakeMove) request);
            break;
        }

        if (response.getResult() == Result.GAME_HAS_ENDED || response.getResult() == Result.CALL_ALREADY_IN_PROCESS ||
            (response.getGameState() != null && response.getGameState().getGameStatus().isEndState())) {
          switch (response.getGameState().getGameStatus()) {
            case WON:
            case WON_BY_TIMEOUT:
              won++;
              break;
            case DRAWN:
              drawn++;
              break;
            case LOST:
            case LOST_BY_TIMEOUT:
              lost++;
              break;
          }
          logger.warn("Game has ended. Result: {} {}-{}. Overall: p={} w={} d={} l={} ({}%)\n",
              response.getGameState().getGameStatus(), response.getGameState().getPlayerStack(), response.getGameState().getOpponentStack(),
              won + drawn + lost, won, drawn, lost, (int) (((double) won / (double) (won + drawn + lost)) * 100));
          response = null;
          bot = botProvider.get();
        }
      } catch (Exception ex) {
        logger.warn("Bot died :( Starting again");
      }
    }
  }


  private void pauseForDramaticEffect(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
