package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class OfferGame extends AbstractBotRequest {

  private final int maximumWaitTime;
  private final int gameStyleId;
  private final boolean dontPlayAgainstSameUser;
  private final boolean dontPlayAgainstSameBot;
  private final String opponentId;

  @Inject
  public OfferGame(BotConfig botConfig) {
    super(botConfig.getBotId(), botConfig.getBotPassword(), null);
    this.maximumWaitTime = botConfig.getMaxWaitTime();
    this.gameStyleId = botConfig.getGameStyle();
    this.dontPlayAgainstSameUser = botConfig.isDontPlaySelfUser();
    this.dontPlayAgainstSameBot = botConfig.isDontPlaySelfBot();
    this.opponentId = botConfig.getOppenentId();
  }

  @JsonProperty("MaximumWaitTime")
  public int getMaximumWaitTime() {
    return maximumWaitTime;
  }

  @JsonProperty("GameStyleId")
  public int getGameStyleId() {
    return gameStyleId;
  }

  @JsonProperty("DontPlayAgainstSameUser")
  public boolean isDontPlayAgainstSameUser() {
    return dontPlayAgainstSameUser;
  }

  @JsonProperty("DontPlayAgainstSameBot")
  public boolean isDontPlayAgainstSameBot() {
    return dontPlayAgainstSameBot;
  }

  @JsonProperty("OpponentId")
  public String getOpponentId() {
    return opponentId;
  }

  @JsonIgnore
  public BotAction getAction() {
    return BotAction.NEW_GAME;
  }
}
