package com.icecreamhead.pokerbot.model;

public class Card {

  private final Suit suit;
  private final Value value;

  public Card(String cardString) {
    this.value = Value.of(cardString.substring(0, 1));
    this.suit = Suit.of(cardString.substring(1, 2));
  }

  public Card(Suit suit, Value value) {
    this.suit = suit;
    this.value = value;
  }

  public Suit getSuit() {
    return suit;
  }

  public Value getValue() {
    return value;
  }

  @Override
  public String toString() {
    return value.name() + "_" + suit.name();
  }
}
