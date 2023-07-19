package com.samuelbatara.dog.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class HttpRequestHandlerTest {
  private HttpRequestHandler handler;

  @BeforeEach
  public void setUp() {
    handler = new HttpRequestHandlerImpl();
  }

  @Test
  public void testRequestAllBreeds() {
    final String url = "https://dog.ceo/api/breeds/list/all";
    final int timeout = 5000;
    String result = null;

    try {
      result = handler.handleRequest(url, timeout, timeout);
    } catch (IOException e) {
      System.out.println("[x] " + e.getMessage());
    }

    Assertions.assertNotNull(result);
  }

  @Test
  public void testRequestByBreed() {
    final String url = "https://dog.ceo/api/breed/hound/images";
    String result = null;

    try {
      result = handler.handleRequest(url);
    } catch (IOException e) {
      System.out.println("[x] " + e.getMessage());
    }

    Assertions.assertNotNull(result);
  }

  @Test
  public void testRequestBySubBreed() {
    final String url = "https://dog.ceo/api/breed/hound/list";
    final int timeout = 2000;
    String result = null;

    try {
      result = handler.handleRequest(url, timeout, timeout);
    } catch (IOException e) {
      System.out.println("[x] " + e.getMessage());
    }

    Assertions.assertNotNull(result);
  }
}
