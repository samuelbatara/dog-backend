package com.samuelbatara.dog.handler;

import java.io.IOException;

public interface HttpRequestHandler {
  String handleRequest(String url) throws IOException;
  String handleRequest(String url, int connectionTimeout, int readTimeout) throws IOException;
}
