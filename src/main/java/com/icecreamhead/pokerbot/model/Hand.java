package com.icecreamhead.pokerbot.model;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.icecreamhead.pokerbot.model.HandUtil.STRAIGHTS;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.concat;
import static java.util.stream.Stream.of;
import static org.assertj.core.util.Lists.emptyList;

public class Hand {
  private final Card hole1;
  private final Card hole2;
  private final List<Card> shared;

  public Hand(List<Card> hole, List<Card> shared) {
    this.hole1 = hole.get(0);
    this.hole2 = hole.get(1);
    this.shared = ImmutableList.copyOf(shared);
  }

  public Hand(Card hole1, Card hole2, List<Card> shared) {
    this.hole1 = hole1;
    this.hole2 = hole2;
    this.shared = ImmutableList.copyOf(shared);
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

  public boolean isPair() {
    return hasMatching(Card::getValue, 2, 1);
  }

  public boolean isHolePair() {
    return hole1.getValue() == hole2.getValue();
  }

  public boolean isHiddenPair() {
    return isPair() && hole1.getValue() != hole2.getValue() && !isSharedPair();
  }
  public boolean isSharedPair() {
    return shared.stream().collect(groupingBy(Card::getValue, counting()))
            .values().stream()
            .filter(l -> l >= 2)
            .count() >= 1;
  }

  public boolean isTwoPair() {
    if (shared.size() < 2) {
      return false;
    }
    return hasMatching(Card::getValue, 2, 2);
  }

  public boolean isThreeOfAKind() {
    if (shared.isEmpty()) {
      return false;
    }
    return hasMatching(Card::getValue, 3, 1);
  }

  public boolean isStraight() {
    if (shared.size() < 3) {
      return false;
    }
    List<Value> cardValues = concat(shared.stream(), of(hole1, hole2)).map(Card::getValue).collect(toList());
    for (List<Value> straight : STRAIGHTS) {
      if (cardValues.containsAll(straight)) {
        return true;
      }
    }
    return false;
  }

  public boolean isFlush() {
    if (shared.size() < 3) {
      return false;
    }
    return hasMatching(Card::getSuit, 5, 1);
  }

  public boolean isFullHouse() {
    if (shared.size() < 3) {
      return false;
    }
    return isThreeOfAKind() && hasMatching(Card::getValue, 2, 2);
  }

  public boolean isFourOfAKind() {
    if (shared.size() < 2) {
      return false;
    }
    return hasMatching(Card::getValue, 4, 1);
  }

  public boolean isStraightFlush() {
    List<Card> suited = suitedCards();
    if (suited.size() < 5) {
      return false;
    }
    List<Value> values = suited.stream().map(Card::getValue).collect(toList());
    for (List<Value> straight : STRAIGHTS) {
      if (values.containsAll(straight)) {
        return true;
      }
    }
    return false;
  }

  private List<Card> suitedCards() {
    if (shared.size() < 3) {
      return emptyList();
    }
    Map<Suit, List<Card>> suits = concat(shared.stream(), of(hole1, hole2)).collect(groupingBy(Card::getSuit));
    for (Map.Entry<Suit, List<Card>> entry : suits.entrySet()) {
      if (entry.getValue().size() >= 5) {
        return entry.getValue();
      }
    }
    return emptyList();
  }

  private boolean hasMatching(Function<Card,Object> mapper, int matchingCount, int numberRequired) {
    return concat(shared.stream(), of(hole1, hole2))
            .collect(groupingBy(mapper, counting()))
            .values().stream()
            .filter(l -> l >= matchingCount)
            .count() >= numberRequired;
  }
 }
