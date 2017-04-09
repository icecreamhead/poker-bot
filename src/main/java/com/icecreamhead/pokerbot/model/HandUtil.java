package com.icecreamhead.pokerbot.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

import static com.icecreamhead.pokerbot.model.Value.ACE;
import static com.icecreamhead.pokerbot.model.Value.EIGHT;
import static com.icecreamhead.pokerbot.model.Value.FIVE;
import static com.icecreamhead.pokerbot.model.Value.FOUR;
import static com.icecreamhead.pokerbot.model.Value.JACK;
import static com.icecreamhead.pokerbot.model.Value.KING;
import static com.icecreamhead.pokerbot.model.Value.NINE;
import static com.icecreamhead.pokerbot.model.Value.QUEEN;
import static com.icecreamhead.pokerbot.model.Value.SEVEN;
import static com.icecreamhead.pokerbot.model.Value.SIX;
import static com.icecreamhead.pokerbot.model.Value.TEN;
import static com.icecreamhead.pokerbot.model.Value.THREE;
import static com.icecreamhead.pokerbot.model.Value.TWO;

public class HandUtil {

  public static final ImmutableList<Value> FACE_CARDS = ImmutableList.of(TEN, JACK, QUEEN, KING, ACE);

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
      ImmutableList.of(ACE, TWO, THREE, FOUR, FIVE),
      ImmutableList.of(TWO, THREE, FOUR, FIVE, SIX),
      ImmutableList.of(THREE, FOUR, FIVE, SIX, SEVEN),
      ImmutableList.of(FOUR, FIVE, SIX, SEVEN, EIGHT),
      ImmutableList.of(FIVE, SIX, SEVEN, EIGHT, NINE),
      ImmutableList.of(SIX, SEVEN, EIGHT, NINE, TEN),
      ImmutableList.of(SEVEN, EIGHT, NINE, TEN, JACK),
      ImmutableList.of(EIGHT, NINE, TEN, JACK, QUEEN),
      ImmutableList.of(NINE, TEN, JACK, QUEEN, KING),
      ImmutableList.of(TEN, JACK, QUEEN, KING, ACE)
  );

  public static final List<Value> PICTURE_CARDS = ImmutableList.of(JACK, QUEEN, KING, ACE);
}
