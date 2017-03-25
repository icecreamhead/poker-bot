package com.icecreamhead.pokerbot;

import com.google.inject.Guice;

import com.icecreamhead.pokerbot.guice.PokerApiModule;

import java.util.concurrent.Executors;

public class Main {

  public static void main(String[] args) {
    BotRunner botRunner = Guice.createInjector(new PokerApiModule()).getInstance(BotRunner.class);

    Executors.newSingleThreadExecutor().submit(botRunner);
  }

}
