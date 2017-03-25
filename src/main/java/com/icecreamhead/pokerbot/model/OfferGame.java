package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OfferGame extends AbstractBotRequest {

  private final int maximumWaitTime;
  private final int gameStyleId;
  private final boolean dontPlayAgainstSameUser;
  private final boolean dontPlayAgainstSameBot;
  private final String opponentId;

  public OfferGame(String botId, String botPassword, int maximumWaitTime, int gameStyleId, boolean dontPlayAgainstSameUser, boolean dontPlayAgainstSameBot, String opponentId) {
    super(botId, botPassword, null);
    this.maximumWaitTime = maximumWaitTime;
    this.gameStyleId = gameStyleId;
    this.dontPlayAgainstSameUser = dontPlayAgainstSameUser;
    this.dontPlayAgainstSameBot = dontPlayAgainstSameBot;
    this.opponentId = opponentId;
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
