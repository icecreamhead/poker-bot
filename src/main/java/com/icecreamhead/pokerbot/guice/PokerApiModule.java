package com.icecreamhead.pokerbot.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.bots.TestBot;
import com.icecreamhead.pokerbot.model.Bot;
import com.icecreamhead.pokerbot.model.BotConfig;

public class PokerApiModule extends AbstractModule {

  protected void configure() {
    bind(ApiConfig.class);
    bind(BotConfig.class);

//    bind(Bot.class).to(NewBot.class);
    bind(Bot.class).to(TestBot.class);
  }

  @Provides
  @Singleton
  public PokerApi providePokerApi(PokerApiProvider pokerApiProvider) {
    return pokerApiProvider.getPokerApi();
  }
}
