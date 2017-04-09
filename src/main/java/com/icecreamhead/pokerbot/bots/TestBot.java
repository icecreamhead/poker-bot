package com.icecreamhead.pokerbot.bots;

import com.google.inject.Inject;

import com.icecreamhead.pokerbot.model.AbstractBot;
import com.icecreamhead.pokerbot.model.AbstractBotRequest;
import com.icecreamhead.pokerbot.model.Bot;
import com.icecreamhead.pokerbot.model.BotConfig;
import com.icecreamhead.pokerbot.model.Card;
import com.icecreamhead.pokerbot.model.GameState;
import com.icecreamhead.pokerbot.model.Hand;
import com.icecreamhead.pokerbot.model.OfferGame;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.icecreamhead.pokerbot.model.HandUtil.hasFaceCard;
import static com.icecreamhead.pokerbot.model.HandUtil.hasPair;
import static com.icecreamhead.pokerbot.model.HandUtil.hasTwoFaceCards;
import static com.icecreamhead.pokerbot.model.HandUtil.isFlush;
import static com.icecreamhead.pokerbot.model.HandUtil.isFourOfAKind;
import static com.icecreamhead.pokerbot.model.HandUtil.isStraight;
import static com.icecreamhead.pokerbot.model.HandUtil.isSuited;
import static com.icecreamhead.pokerbot.model.HandUtil.isThreeOfAKind;
import static com.icecreamhead.pokerbot.model.HandUtil.isTwoPair;

public class TestBot extends AbstractBot implements Bot {

  @Inject
  public TestBot(BotConfig config, OfferGame offerGame) {
    super(config, offerGame);
  }

  @Override
  protected AbstractBotRequest makeGameDecision(GameState gameState) {
    Hand hand = gameState.getHand();
    logger.info("Making decision. State is {}: {}", hand.bettingRound(), hand.toString());

    int multiplier = 1;
    if (gameState.getPlayerStack() + 100 < gameState.getOpponentStack()) {
      logger.info("I'm going for broke!");
      multiplier = 3;
    }

    int bet = calcBet(gameState.getPlayerHand(), gameState.getBoardCards(), multiplier);

    if (gameState.getPlayerStack() > gameState.getOpponentStack() + 60 && bet < 20) {
      logger.info("I'm holding on the my lead!");
      return bet(0);
    }

    if (bet >= 0) {
      logger.info("Decided to bet {}\n", bet);
      return bet(bet);
    }

    logger.info("Decided to checkOrFold\n");
    return checkOrFold();
  }

  private int calcBet(List<Card> playerHand, List<Card> boardCards, int multiplier) {
    int bet = 0;

    switch (boardCards.size()) {
      case 0:
        if (hasTwoFaceCards(playerHand)) {
          bet += 50;
        }
        if (hasFaceCard(playerHand)) {
          bet += 30;
        }
        if (hasPair(playerHand)) {
          bet += 30;
        }

        if (isSuited(playerHand)) {
          bet += 30;
        }
        return bet * multiplier;
      case 3:
      case 4:
      case 5:
        List<Card> allCards = Stream.concat(playerHand.stream(), boardCards.stream()).collect(Collectors.toList());
        if (isFlush(allCards)) {
          logger.info("Hand is a flush");
          bet += 100;
        }
        if (isStraight(allCards)) {
          logger.info("Hand is a straight");
          bet += 50;
        }
        if (isFourOfAKind(allCards)) {
          logger.info("Hand is four of a kind");
          bet += 50;
        }
        if (isThreeOfAKind(allCards)) {
          logger.info("Hand is three of a kind");
          bet += 30;
        }
        if (isTwoPair(allCards)) {
          logger.info("Hand is two pair");
          bet += 20;
        }
        return bet * multiplier;
      default:
        return 0;
    }

//    return -1;
  }

}
