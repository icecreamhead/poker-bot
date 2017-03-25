package com.icecreamhead.pokerbot.model;

public interface Bot {

  AbstractBotRequest handleResponse(ServerResponse response);

}
