package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public class PollForGameState extends AbstractBotRequest {

  private final int maximumWaitTime;

  public PollForGameState(String botId, String botPassword, int maximumWaitTime, UUID playerKey) {
    super(botId, botPassword, playerKey);
    this.maximumWaitTime = maximumWaitTime;
  }

  @JsonProperty("MaximumWaitTime")
  public int getMaximumWaitTime() {
    return maximumWaitTime;
  }

  public BotAction getAction() {
    return BotAction.POLL_FOR_GAME_STATE;
  }
}
