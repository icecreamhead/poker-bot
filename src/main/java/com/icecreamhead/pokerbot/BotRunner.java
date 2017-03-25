package com.icecreamhead.pokerbot;

import com.google.inject.Inject;

import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.model.Bot;
import com.icecreamhead.pokerbot.model.BotRequest;
import com.icecreamhead.pokerbot.model.OfferGame;
import com.icecreamhead.pokerbot.model.ServerResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BotRunner implements Runnable {
  private static final Logger logger = LoggerFactory.getLogger(BotRunner.class);

  private final Bot bot;
  private final PokerApi pokerApi;

  @Inject
  public BotRunner(Bot bot, PokerApi pokerApi) {
    this.bot = bot;
    this.pokerApi = pokerApi;
  }

  public void run() {
    logger.info("Starting!");
    // Attempt to connect!

    ServerResponse response = null;
    while (true) {
      BotRequest request = bot.handleResponse(response);

      if (request == null) {
        logger.error("No action selected! Exiting :(");
        break;
      }

      switch (request.getAction()) {
        case NEW_GAME:
          response = pokerApi.offerGame((OfferGame) request);
          break;
      }
    }
  }

}
