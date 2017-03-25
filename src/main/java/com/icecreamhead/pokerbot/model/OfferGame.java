package com.icecreamhead.pokerbot.model;

public class OfferGame implements BotRequest {

  private final String BotId;
  private final String BotPassword;
  private final int MaximumWaitTime;
  private final int GameStyleId;
  private final boolean DontPlayAgainstSameUser;
  private final boolean DontPlayAgainstSameBot;
  private final String OpponentId;

  public OfferGame(String botId, String botPassword, int maximumWaitTime, int gameStyleId, boolean dontPlayAgainstSameUser, boolean dontPlayAgainstSameBot, String opponentId) {
    BotId = botId;
    BotPassword = botPassword;
    MaximumWaitTime = maximumWaitTime;
    GameStyleId = gameStyleId;
    DontPlayAgainstSameUser = dontPlayAgainstSameUser;
    DontPlayAgainstSameBot = dontPlayAgainstSameBot;
    OpponentId = opponentId;
  }

  public String getBotId() {
    return BotId;
  }

  public String getBotPassword() {
    return BotPassword;
  }

  public int getMaximumWaitTime() {
    return MaximumWaitTime;
  }

  public int getGameStyleId() {
    return GameStyleId;
  }

  public boolean isDontPlayAgainstSameUser() {
    return DontPlayAgainstSameUser;
  }

  public boolean isDontPlayAgainstSameBot() {
    return DontPlayAgainstSameBot;
  }

  public String getOpponentId() {
    return OpponentId;
  }

  public BotAction getAction() {
    return BotAction.NEW_GAME;
  }

  @Override
  public String toString() {
    return "OfferGame{" +
        "BotId='" + BotId + '\'' +
        ", BotPassword='" + BotPassword + '\'' +
        ", MaximumWaitTime=" + MaximumWaitTime +
        ", GameStyleId=" + GameStyleId +
        ", DontPlayAgainstSameUser=" + DontPlayAgainstSameUser +
        ", DontPlayAgainstSameBot=" + DontPlayAgainstSameBot +
        ", OpponentId='" + OpponentId + '\'' +
        '}';
  }
}
