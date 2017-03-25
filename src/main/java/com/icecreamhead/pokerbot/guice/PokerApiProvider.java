package com.icecreamhead.pokerbot.guice;

import com.google.inject.Inject;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.rest.MessageLogger;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class PokerApiProvider {
  private static final Logger logger = LoggerFactory.getLogger(PokerApiProvider.class);

  private final Class<PokerApi> pokerApiClass = PokerApi.class;

  private final ApiConfig apiConfig;

  @Inject
  public PokerApiProvider(ApiConfig apiConfig) {
    this.apiConfig = apiConfig;
  }

  PokerApi getPokerApi() {
    ResteasyClientBuilder resteasyClientBuilder = new ResteasyClientBuilder()
        .httpEngine(httpEngine());

    resteasyClientBuilder.register(new JacksonJsonProvider());
    resteasyClientBuilder.register(new MessageLogger());

    PokerApi pokerApi = resteasyClientBuilder.build().target(apiConfig.getApiUri()).proxy(pokerApiClass);
    MethodExecutor methodExecutor = new MethodExecutor(pokerApi);
    return pokerApiClass.cast(Proxy.newProxyInstance(pokerApiClass.getClassLoader(), new Class<?>[]{pokerApiClass}, methodExecutor));
  }

  private static ClientHttpEngine httpEngine() {
    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectionRequestTimeout(10000)
        .setConnectTimeout(3000)
        .setSocketTimeout(3000)
        .build();

    HttpClient httpClient = HttpClientBuilder.create()
        .setDefaultRequestConfig(requestConfig)
        .build();

    return new ApacheHttpClient4Engine(httpClient);
  }

  private static class MethodExecutor implements InvocationHandler {

    private final Object target;

    private MethodExecutor(Object target) {
      this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      return method.invoke(target, args);
    }
  }

}

