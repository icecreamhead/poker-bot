package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameState {
  /*
  {"GameStatus":"RUNNING",
  "BigBlind":20,
  "BoardCards":[],
  "DealCount":10,
  "DealNumber":1,
  "IsDealer":true,
  "MandatoryResponse":true,
  "OpponentHand":["XX","XX"],
  "OpponentRoundBetTotal":20,
  "OpponentStack":981,
  "PlayerHand":["TD","5S"],
  "PlayerRoundBetTotal":10,
  "PlayerStack":990,
  "PotAfterPreviousRound":0,
  "ResponseDeadline":"2017-03-25T17:03:19.412346Z",
  "Round":0,"SmallBlind":10,
  "GameId":437892,
  "IsMover":true}
   */

  private final GameStatus gameStatus;
  private final int bigBlind;
  private final String[] boardCards;
  private final int dealCount;
  private final int dealNumber;
  private final boolean dealer;
  private final boolean mandatoryResponse;
  private final String[] opponentHand;
  private final int opponentRoundBetTotal;
  private final int opponentStack;
  private final String[] playerHand;
  private final int playerRoundBetTotal;
  private final int playerStack;
  private final int potAfterPreviousRound;
  private final String responseDeadline;
  private final int round;
  private final int smallBlind;
  private final int gameId;
  private final boolean mover;

  public GameState(
      @JsonProperty("GameStatus") GameStatus gameStatus,
      @JsonProperty("BigBlind") int bigBlind,
      @JsonProperty("BoardCards") String[] boardCards,
      @JsonProperty("DealCount") int dealCount,
      @JsonProperty("DealNumber") int dealNumber,
      @JsonProperty("IsDealer") boolean dealer,
      @JsonProperty("MandatoryResponse") boolean mandatoryResponse,
      @JsonProperty("OpponentHand") String[] opponentHand,
      @JsonProperty("OpponentRoundBetTotal") int opponentRoundBetTotal,
      @JsonProperty("OpponentStack") int opponentStack,
      @JsonProperty("PlayerHand") String[] playerHand,
      @JsonProperty("PlayerRoundBetTotal") int playerRoundBetTotal,
      @JsonProperty("PlayerStack") int playerStack,
      @JsonProperty("PotAfterPreviousRound") int potAfterPreviousRound,
      @JsonProperty("ResponseDeadline") String responseDeadline,
      @JsonProperty("Round") int round,
      @JsonProperty("SmallBlind") int smallBlind,
      @JsonProperty("GameId") int gameId,
      @JsonProperty("IsMover") boolean mover) {
    this.gameStatus = gameStatus;
    this.bigBlind = bigBlind;
    this.boardCards = boardCards;
    this.dealCount = dealCount;
    this.dealNumber = dealNumber;
    this.dealer = dealer;
    this.mandatoryResponse = mandatoryResponse;
    this.opponentHand = opponentHand;
    this.opponentRoundBetTotal = opponentRoundBetTotal;
    this.opponentStack = opponentStack;
    this.playerHand = playerHand;
    this.playerRoundBetTotal = playerRoundBetTotal;
    this.playerStack = playerStack;
    this.potAfterPreviousRound = potAfterPreviousRound;
    this.responseDeadline = responseDeadline;
    this.round = round;
    this.smallBlind = smallBlind;
    this.gameId = gameId;
    this.mover = mover;
  }

  public GameStatus getGameStatus() {
    return gameStatus;
  }

  public int getBigBlind() {
    return bigBlind;
  }

  public String[] getBoardCards() {
    return boardCards;
  }

  public int getDealCount() {
    return dealCount;
  }

  public int getDealNumber() {
    return dealNumber;
  }

  public boolean isDealer() {
    return dealer;
  }

  public boolean isMandatoryResponse() {
    return mandatoryResponse;
  }

  public String[] getOpponentHand() {
    return opponentHand;
  }

  public int getOpponentRoundBetTotal() {
    return opponentRoundBetTotal;
  }

  public int getOpponentStack() {
    return opponentStack;
  }

  public String[] getPlayerHand() {
    return playerHand;
  }

  public int getPlayerRoundBetTotal() {
    return playerRoundBetTotal;
  }

  public int getPlayerStack() {
    return playerStack;
  }

  public int getPotAfterPreviousRound() {
    return potAfterPreviousRound;
  }

  public String getResponseDeadline() {
    return responseDeadline;
  }

  public int getRound() {
    return round;
  }

  public int getSmallBlind() {
    return smallBlind;
  }

  public int getGameId() {
    return gameId;
  }

  public boolean isMover() {
    return mover;
  }
}
