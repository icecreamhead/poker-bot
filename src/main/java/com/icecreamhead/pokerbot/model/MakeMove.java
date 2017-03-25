package com.icecreamhead.pokerbot.model;

import java.util.UUID;

public class MakeMove extends AbstractBotRequest {

  private final Move move;

  public MakeMove(String botId, String botPassword, UUID playerKey, Move move) {
    super(botId, botPassword, playerKey);
    this.move = move;
  }

  public Move getMove() {
    return move;
  }

  @Override
  public BotAction getAction() {
    return BotAction.MAKE_MOVE;
  }
}
