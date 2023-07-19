package com.samuelbatara.dog.model;

public enum StatusType {
  SUCCESS("success"), FAILED("failed");

  private final String value;
  StatusType(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }
}
