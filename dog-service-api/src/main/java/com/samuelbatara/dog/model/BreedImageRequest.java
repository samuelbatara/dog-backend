package com.samuelbatara.dog.model;

import lombok.Data;

@Data
public class BreedImageRequest {
  private static final String _PREFIX = "https://dog.ceo/api/breed/";
  private String breed;
  private int size;
  private String url;

  public BreedImageRequest(String breed, int size) {
    this.breed = breed;
    this.size = size;
    this.url = _PREFIX + breed + "/images";
  }
}
