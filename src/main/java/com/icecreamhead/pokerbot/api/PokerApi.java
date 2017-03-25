package com.icecreamhead.pokerbot.api;

import com.icecreamhead.pokerbot.model.GameStateResponse;
import com.icecreamhead.pokerbot.model.OfferGame;
import com.icecreamhead.pokerbot.model.PollForGameState;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("Api")
public interface PokerApi {

  @POST
  @Path("OfferGame")
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  GameStateResponse offerGame(OfferGame offerGame);

  @POST
  @Path("PollForGameState")
  @Produces(APPLICATION_JSON)
  @Consumes(APPLICATION_JSON)
  GameStateResponse pollForGameState(PollForGameState pollForGameState);

}
