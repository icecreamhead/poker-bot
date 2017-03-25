package com.icecreamhead.pokerbot.model;

import java.util.UUID;

public class PollForGameState implements BotRequest {

  private final String BotId;
  private final String BotPassword;
  private final int MaximumWaitTime;
  private final UUID playerKey;

  public PollForGameState(String botId, String botPassword, int maximumWaitTime, UUID playerKey) {
    BotId = botId;
    BotPassword = botPassword;
    MaximumWaitTime = maximumWaitTime;
    this.playerKey = playerKey;
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

  public UUID getPlayerKey() {
    return playerKey;
  }

  public BotAction getAction() {
    return BotAction.POLL_FOR_GAME_STATE;
  }
}
