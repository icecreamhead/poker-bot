package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public abstract class AbstractBotRequest {

  private final String botId;
  private final String botPassword;
  private final UUID playerKey;

  public AbstractBotRequest(String botId, String botPassword, UUID playerKey) {
    this.botId = botId;
    this.botPassword = botPassword;
    this.playerKey = playerKey;
  }

  @JsonProperty("BotId")
  public String getBotId() {
    return botId;
  }

  @JsonProperty("BotPassword")
  public String getBotPassword() {
    return botPassword;
  }

  @JsonProperty("PlayerKey")
  @JsonInclude(value = JsonInclude.Include.NON_NULL)
  public UUID getPlayerKey() {
    return playerKey;
  }

  @JsonIgnore
  public abstract BotAction getAction();
}
