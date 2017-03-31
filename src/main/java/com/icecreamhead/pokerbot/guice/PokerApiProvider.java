package com.icecreamhead.pokerbot.guice;

import com.google.inject.Inject;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.icecreamhead.pokerbot.api.PokerApi;
import com.icecreamhead.pokerbot.rest.MessageLogger;

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
        .httpEngine(new ApacheHttpClient4Engine());

    resteasyClientBuilder.register(new JacksonJsonProvider());
    resteasyClientBuilder.register(new MessageLogger());

    PokerApi pokerApi = resteasyClientBuilder.build().target(apiConfig.getApiUri()).proxy(pokerApiClass);
    MethodExecutor methodExecutor = new MethodExecutor(pokerApi);
    return pokerApiClass.cast(Proxy.newProxyInstance(pokerApiClass.getClassLoader(), new Class<?>[]{pokerApiClass}, methodExecutor));
  }

  private static class MethodExecutor implements InvocationHandler {

    private final Object target;

    private MethodExecutor(Object target) {
      this.target = target;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      int i = 0;
      while (true) {
        try {
          return method.invoke(target, args);
        } catch (Exception ex) {
          logger.error("Failed to call method {}", method.getName(), ex);
          i++;
          if (i >= 3) {
            throw ex;
          }
        }
      }
    }
  }

}

