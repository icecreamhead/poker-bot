package com.icecreamhead.pokerbot.guice;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.net.URI;
import java.net.URISyntaxException;

import static com.icecreamhead.pokerbot.guice.Property.API_URL;

@Singleton
public class ApiConfig {

    private final URI apiUrl;

    @Inject
    public ApiConfig(@Config(API_URL) String apiUrl) throws URISyntaxException {
        this.apiUrl = new URI(apiUrl);
    }

    public URI getApiUrl() {
        return apiUrl;
    }
}
