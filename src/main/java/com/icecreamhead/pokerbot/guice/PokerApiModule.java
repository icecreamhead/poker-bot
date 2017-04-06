package com.icecreamhead.pokerbot.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.model.Bot;
import com.icecreamhead.pokerbot.model.BotConfig;
import com.icecreamhead.pokerbot.model.TestBot;

public class PokerApiModule extends AbstractModule {

  protected void configure() {
    bind(ApiConfig.class);
//    bind(MessageLogger.class);

    bind(Bot.class).to(TestBot.class);
    bind(BotConfig.class);
  }

  @Provides
  @Singleton
  public PokerApi providePokerApi(PokerApiProvider pokerApiProvider) {
    return pokerApiProvider.getPokerApi();
  }
}
