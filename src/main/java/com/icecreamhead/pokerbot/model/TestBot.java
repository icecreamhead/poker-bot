package com.icecreamhead.pokerbot.model;

import com.google.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;
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

public class TestBot implements Bot {

  private static final Logger logger = LoggerFactory.getLogger(TestBot.class);

  private final String botId;
  private final String botpassword;

  private UUID playerKey;

  @Inject
  public TestBot(BotConfig config) {
    this.botId = config.getBotId();
    this.botpassword = config.getBotPassword();
  }

  @Override
  public AbstractBotRequest handleResponse(ServerResponse response) {
    if (response == null) {
      return offerGame();
    }

    switch (response.getResponseType()) {
      case OFFER_GAME_RESPONSE:
        return handleGameStateResponse((GameStateResponse) response);
    }

    return null;
  }

  private OfferGame offerGame() {
    return new OfferGame(botId, botpassword,
        5000, 10, false, false, null);
  }

  private AbstractBotRequest handleGameStateResponse(GameStateResponse gameStateResponse) {
    logger.info("Handling game state: {}", gameStateResponse.getResult());
    if (gameStateResponse.getPlayerKey() != null) {
      playerKey = gameStateResponse.getPlayerKey();
    }

    switch (gameStateResponse.getResult()) {
      case NOT_YOUR_MOVE:
      case WAITING_FOR_GAME:
        return waitForTurn();

      case SUCCESS:
        if (gameStateResponse.getGameState().isMover()) {
          return makeGameDecision(gameStateResponse.getGameState());
        } else {
          return waitForTurn();
        }

      case INVALID_MOVE:
        return fold();

      default:
        throw new RuntimeException(gameStateResponse.getResult().name());
    }
  }

  private AbstractBotRequest waitForTurn() {
    return new PollForGameState(botId, botpassword, 5000, playerKey);
  }

  private AbstractBotRequest makeGameDecision(GameState gameState) {
    logger.info("Making decision. Hand is {}. Board is {}", gameState.getPlayerHand(), gameState.getBoardCards());

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

    logger.info("Decided to fold\n");
    return fold();
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

  private MakeMove fold() {
    return new MakeMove(botId, botpassword, playerKey, new Move(true, 0));
  }

  private MakeMove bet(int bet) {
    return new MakeMove(botId, botpassword, playerKey, new Move(false, bet));
  }

}
