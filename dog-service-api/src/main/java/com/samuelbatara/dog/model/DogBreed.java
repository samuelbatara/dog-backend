package com.samuelbatara.dog.model;

public enum DogBreed {
  SHIBA("shiba"), SHEEPDOG("sheepdog"), TERRIER("terrier");

  private String breed;
  DogBreed(String breed) {
    this.breed = breed;
  }

  public String getBreed() {
    return breed;
  }
}
