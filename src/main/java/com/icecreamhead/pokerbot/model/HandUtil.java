package com.icecreamhead.pokerbot.model;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class HandUtil {

  public static final ImmutableList<Value> FACE_CARDS = ImmutableList.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING, Value.ACE);

  public static boolean hasFaceCard(List<Card> cards) {
    return cards.stream().map(Card::getValue).anyMatch(FACE_CARDS::contains);
  }

  public static boolean hasPair(List<Card> cards) {
    return cards.stream().map(Card::getValue).count() != cards.stream().map(Card::getValue).distinct().count();
  }

  public static boolean isSuited(List<Card> cards) {
    return cards.stream().map(Card::getSuit).distinct().count() == 1;
  }
}
