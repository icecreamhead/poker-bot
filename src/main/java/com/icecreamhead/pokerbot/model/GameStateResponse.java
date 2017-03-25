package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


public class GameStateResponse implements ServerResponse {

  private final Result result;
  private final UUID playerKey;
  private final long balance;
  private final GameState gameState;

  public GameStateResponse(
      @JsonProperty("Result") Result result,
      @JsonProperty("PlayerKey") UUID playerKey,
      @JsonProperty("Balance") long balance,
      @JsonProperty("GameState") GameState gameState) {
    this.result = result;
    this.playerKey = playerKey;
    this.balance = balance;
    this.gameState = gameState;
  }

  @JsonProperty("Result")
  public Result getResult() {
    return result;
  }

  @JsonProperty("PlayerKey")
  public UUID getPlayerKey() {
    return playerKey;
  }

  @JsonProperty("Balance")
  public long getBalance() {
    return balance;
  }

  @JsonProperty("GameState")
  public GameState getGameState() {
    return gameState;
  }

  @Override
  public String toString() {
    return "GameStateResponse{" +
        "result=" + result +
        ", playerKey=" + playerKey +
        ", balance=" + balance +
        ", gameState=" + gameState +
        '}';
  }

  public ServerResponseType getResponseType() {
    return ServerResponseType.OFFER_GAME_RESPONSE;
  }
}
