package com.icecreamhead.pokerbot.guice;

import java.net.URI;
import java.net.URISyntaxException;

public class ApiConfig {

  private final URI hostname;

  public ApiConfig() throws URISyntaxException {
    this.hostname = new URI("http://beta.aigaming.com");
  }

  public URI getApiUri() {
    return hostname;
  }
}
