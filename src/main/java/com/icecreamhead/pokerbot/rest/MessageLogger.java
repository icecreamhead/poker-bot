package com.icecreamhead.pokerbot.rest;

import com.google.common.io.ByteStreams;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.ReaderInterceptor;
import javax.ws.rs.ext.ReaderInterceptorContext;
import javax.ws.rs.ext.WriterInterceptor;
import javax.ws.rs.ext.WriterInterceptorContext;

public class MessageLogger implements ReaderInterceptor, WriterInterceptor {
  private static final Logger LOG = LoggerFactory.getLogger(MessageLogger.class);

  public Object aroundReadFrom(ReaderInterceptorContext context) throws IOException, WebApplicationException {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ByteStreams.copy(context.getInputStream(), buffer);
    byte[] bytes = buffer.toByteArray();
    LOG.info("Received response: {}", new String(bytes, "UTF-8"));
    context.setInputStream(new ByteArrayInputStream(bytes));
    return context.proceed();
  }

  public void aroundWriteTo(WriterInterceptorContext context) throws IOException, WebApplicationException {
    OutputStream target = context.getOutputStream();
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    context.setOutputStream(buffer);
    context.proceed();
    byte[] bytes = buffer.toByteArray();
    LOG.debug("Sending request: {}", new String(bytes, "UTF-8"));
    target.write(bytes);
  }

}
