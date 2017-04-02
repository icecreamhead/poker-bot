package com.icecreamhead.pokerbot;

import com.google.inject.Guice;

import com.icecreamhead.pokerbot.guice.ConfigModule;
import com.icecreamhead.pokerbot.guice.PokerApiModule;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {
    BotRunner botRunner = Guice.createInjector(
            new ConfigModule(),
            new PokerApiModule()
    ).getInstance(BotRunner.class);

//    Executors.newSingleThreadExecutor().submit(botRunner);
    new Thread(botRunner, "BotRunnerThread").start();
  }

}
