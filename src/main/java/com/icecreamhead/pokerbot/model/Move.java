package com.icecreamhead.pokerbot.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Move {
  private final boolean fold;
  private final int betSize;

  public Move(boolean fold, int betSize) {
    this.fold = fold;
    this.betSize = betSize;
  }

  @JsonProperty("IsFold")
  public boolean isFold() {
    return fold;
  }

  @JsonProperty("BetSize")
  public int getBetSize() {
    return betSize;
  }

  @Override
  public String toString() {
    return "Move{" +
        "fold=" + fold +
        ", betSize=" + betSize +
        '}';
  }
}
