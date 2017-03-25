package com.icecreamhead.pokerbot.model;

public interface Bot {

  BotRequest handleResponse(ServerResponse response);

}
