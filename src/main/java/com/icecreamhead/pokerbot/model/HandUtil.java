package com.icecreamhead.pokerbot.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class HandUtil {

  public static final ImmutableList<Value> FACE_CARDS = ImmutableList.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING, Value.ACE);

  public static boolean hasFaceCard(List<Card> cards) {
    return cards.stream().map(Card::getValue).anyMatch(FACE_CARDS::contains);
  }

  public static boolean hasTwoFaceCards(List<Card> cards) {
    return cards.stream().map(Card::getValue).filter(FACE_CARDS::contains).count() >= 2;
  }

  public static boolean hasPair(List<Card> cards) {
    return cards.stream().map(Card::getValue).count() != cards.stream().map(Card::getValue).distinct().count();
  }

  public static boolean isSuited(List<Card> cards) {
    return cards.stream().map(Card::getSuit).distinct().count() == 1;
  }

  public static boolean isTwoPair(List<Card> cards) {
    return cards.stream().collect(Collectors.groupingBy(Card::getValue, Collectors.counting())).values().stream().filter(l -> l > 1).count() >= 2;
  }

  public static boolean isThreeOfAKind(List<Card> cards) {
    return cards.stream().collect(Collectors.groupingBy(Card::getValue, Collectors.counting())).values().stream().filter(l -> l > 1).count() >= 3;
  }

  public static boolean isFourOfAKind(List<Card> cards) {
    return cards.stream().collect(Collectors.groupingBy(Card::getValue, Collectors.counting())).values().stream().filter(l -> l > 1).count() >= 4;
  }

  public static boolean isFlush(List<Card> cards) {
    return cards.stream().collect(Collectors.groupingBy(Card::getSuit, Collectors.counting())).values().stream().filter(c -> c >= 5).count() >= 1;
  }

  public static boolean isStraight(List<Card> cards) {
    if (cards.size() < 5) {
      return false;
    }
    List<Value> cardValues = cards.stream().map(Card::getValue).collect(Collectors.toList());
    for (List<Value> straight : STRAIGHTS) {
      if (cardValues.containsAll(straight)) {
        return true;
      }
    }
    return false;
  }

  public static final List<List<Value>> STRAIGHTS = ImmutableList.of(
      ImmutableList.of(Value.ACE, Value.TWO, Value.THREE, Value.FOUR, Value.FIVE),
      ImmutableList.of(Value.TWO, Value.THREE, Value.FOUR, Value.FIVE, Value.SIX),
      ImmutableList.of(Value.THREE, Value.FOUR, Value.FIVE, Value.SIX, Value.SEVEN),
      ImmutableList.of(Value.FOUR, Value.FIVE, Value.SIX, Value.SEVEN, Value.EIGHT),
      ImmutableList.of(Value.FIVE, Value.SIX, Value.SEVEN, Value.EIGHT, Value.NINE),
      ImmutableList.of(Value.SIX, Value.SEVEN, Value.EIGHT, Value.NINE, Value.TEN),
      ImmutableList.of(Value.SEVEN, Value.EIGHT, Value.NINE, Value.TEN, Value.JACK),
      ImmutableList.of(Value.EIGHT, Value.NINE, Value.TEN, Value.JACK, Value.QUEEN),
      ImmutableList.of(Value.NINE, Value.TEN, Value.JACK, Value.QUEEN, Value.KING),
      ImmutableList.of(Value.TEN, Value.JACK, Value.QUEEN, Value.KING, Value.ACE)
  );
}
