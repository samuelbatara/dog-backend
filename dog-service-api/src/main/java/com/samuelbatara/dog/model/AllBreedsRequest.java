package com.samuelbatara.dog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllBreedsRequest {
  private static final String _URL = "https://dog.ceo/api/breeds/list/all";
  private String url;
  private int connectionTimeout;
  private int readTimeout;

  public String getUrl() {
    if (url == null) {
      url = _URL;
    }

    return url;
  }
}
