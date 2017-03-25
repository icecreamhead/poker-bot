package com.icecreamhead.pokerbot.model;

public enum Value {
  UNKNOWN("X"),
  TWO("2"),
  THREE("3"),
  FOUR("4"),
  FIVE("5"),
  SIX("6"),
  SEVEN("7"),
  EIGHT("8"),
  NINE("8"),
  TEN("T"),
  JACK("J"),
  QUEEN("Q"),
  KING("K"),
  ACE("A");

  private final String sym;

  Value(String sym) {
    this.sym = sym;
  }

  public static Value of(String sym) {
    for (Value v : values()) {
      if (v.sym.equals(sym)) {
        return v;
      }
    }
    return null;
  }
}
