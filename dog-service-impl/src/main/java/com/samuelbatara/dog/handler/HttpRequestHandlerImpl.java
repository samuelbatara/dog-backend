package com.samuelbatara.dog.handler;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Slf4j
public class HttpRequestHandlerImpl implements HttpRequestHandler {
  @Override
  public String handleRequest(String url) throws IOException {
    return handleRequest(url, 5000, 5000);
  }

  @Override
  public String handleRequest(String url, int connectionTimeout, int readTimeout) throws IOException {
    HttpURLConnection connection = null;
    try {
      connection = (HttpURLConnection) new URL(url).openConnection();
      connection.setRequestMethod("GET");
      connection.setConnectTimeout(connectionTimeout);
      connection.setReadTimeout(readTimeout);
      connection.setDoOutput(true);
    } catch (ProtocolException e) {
      throw new RuntimeException(e);
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    // read response
    String result = "";
    InputStream inputStream = null;
    ByteArrayOutputStream byteArrayOutputStream = null;
    byte[] buffer = new byte[1024];
    int length = 0;
    try {
      inputStream = connection.getInputStream();
      byteArrayOutputStream = new ByteArrayOutputStream();
      while ( (length = inputStream.read(buffer)) != -1 ) {
        byteArrayOutputStream.write(buffer, 0, length);
      }
    } catch (IOException e) {
      log.error("[x] Failed to request: " + url);
      return "";
    } finally {
      if (byteArrayOutputStream != null) {
        result = byteArrayOutputStream.toString(StandardCharsets.UTF_8);
        inputStream.close();
        byteArrayOutputStream.close();
      }
    }

    return result;
  }
}
