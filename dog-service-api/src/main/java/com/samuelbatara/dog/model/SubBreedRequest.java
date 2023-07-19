package com.samuelbatara.dog.model;

import lombok.Data;

@Data
public class SubBreedRequest {
  static private final String _PREFIX = "https://dog.ceo/api/breed/";
  private String breed;
  private int connectionTimeout;
  private int readTimeout;
  private String url;


  public SubBreedRequest(String breed, int connectionTimeout, int readTimeout) {
    this.breed = breed;
    this.connectionTimeout = connectionTimeout;
    this.readTimeout = readTimeout;
    this.url = _PREFIX + breed + "/list";
  }
}
