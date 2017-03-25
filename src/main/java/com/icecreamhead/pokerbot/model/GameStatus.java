package com.icecreamhead.pokerbot.model;

public enum GameStatus {
  STARTING_SOON(false),
  RUNNING(false),
  WON(true),
  WON_BY_TIMEOUT(true),
  LOST(true),
  LOST_BY_TIMEOUT(true),
  DRAWN(true);

  private final boolean endState;

  GameStatus(boolean endState) {
    this.endState = endState;
  }

  public boolean isEndState() {
    return endState;
  }
}
