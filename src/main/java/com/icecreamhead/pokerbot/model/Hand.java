package com.icecreamhead.pokerbot.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class Hand {
  private final Card hole1;
  private final Card hole2;
  private final List<Card> shared;

  public Hand(List<Card> hole, List<Card> shared) {
    this.hole1 = hole.get(0);
    this.hole2 = hole.get(1);
    this.shared = shared;
  }

  public List<Card> getHole() {
    return ImmutableList.of(hole1, hole2);
  }

  public Card getHole1() {
    return hole1;
  }

  public Card getHole2() {
    return hole2;
  }

  public List<Card> getShared() {
    return shared;
  }

  public boolean isHolePair() {
    return hole1.getValue() == hole2.getValue();
  }

  public boolean isHiddenPair() {
    List<Value> sharedValues = shared.stream().map(Card::getValue).collect(Collectors.toList());
    return sharedValues.contains(hole1.getValue()) || sharedValues.contains(hole2.getValue());
  }
}
