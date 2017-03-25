package com.icecreamhead.pokerbot.model;

public enum Result {
  SUCCESS,
  CALL_ALREADY_IN_PROCESS,
  WAITING_FOR_GAME,
  GAME_ALREADY_STARTED,
  GAME_HAS_ENDED,
  INVALID_LOGIN_OR_PASSWORD,
  INVALID_PLAYER_KEY,
  BOT_IS_SUSPENDED,
  BOT_IS_INACTIVE,
  INSUFFICIENT_BALANCE,
  NOT_YOUR_MOVE,
  INVALID_MOVE
}
