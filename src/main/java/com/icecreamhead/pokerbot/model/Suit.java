package com.icecreamhead.pokerbot.model;

public enum Suit {
  HEARTS("H"), CLUBS("C"), DIAMONDS("D"), SPADES("S"), UNKNOWN("X");

  private final String sym;

  Suit(String sym) {
    this.sym = sym;
  }

  public static Suit of(String sym) {
    for (Suit s : values()) {
      if (s.sym.equals(sym)) {
        return s;
      }
    }
    return null;
  }
}
