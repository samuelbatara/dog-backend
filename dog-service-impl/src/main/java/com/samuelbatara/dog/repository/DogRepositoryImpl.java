package com.samuelbatara.dog.repository;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.samuelbatara.dog.handler.HttpRequestHandler;
import com.samuelbatara.dog.model.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class DogRepositoryImpl implements DogRepository {
  private final HttpRequestHandler handler;
  private final Gson gson;

  public DogRepositoryImpl(HttpRequestHandler handler) {
    this.handler = handler;
    gson = new Gson();
  }

  @Override
  public AllBreedsResponse findAllBreeds(AllBreedsRequest request) {
    String result = "";
    try {
      result = handler.handleRequest(request.getUrl(), request.getConnectionTimeout(),
          request.getReadTimeout());
    } catch (IOException e) {
      log.error("[x] Failed to request: " + request);
      log.error("[x] With error: " + e.getMessage());
      return new AllBreedsResponse(null, StatusType.FAILED.getValue());
    }

    if (result == "") {
      return new AllBreedsResponse(null, StatusType.FAILED.getValue());
    }

    AllBreedsResponse response;
    try {
      response = gson.fromJson(result, AllBreedsResponse.class);
    } catch (JsonSyntaxException e) {
      log.error("[x] Failed to convert: " + result);
      log.error("[x] With error: " + e.getMessage());
      return new AllBreedsResponse(null, StatusType.FAILED.getValue());
    }

    if (response == null) {
      return new AllBreedsResponse(null, StatusType.FAILED.getValue());
    }

    return response;
  }

  @Override
  public BreedImageResponse findBreedImages(BreedImageRequest request) {
    String result = "";
    try {
      result = handler.handleRequest(request.getUrl());
    } catch (IOException e) {
      log.error("[x] Failed to request: " + request);
      log.error("[x] With error: " + e.getMessage());
      return new BreedImageResponse(null, StatusType.FAILED.getValue());
    }

    if (result == "") {
      return new BreedImageResponse(null, StatusType.FAILED.getValue());
    }

    BreedImageResponse response;
    try {
      response = gson.fromJson(result, BreedImageResponse.class);
    } catch (JsonSyntaxException e) {
      log.error("[x] Failed to convert: " + result);
      log.error("[x] With error: " + e.getMessage());
      return new BreedImageResponse(null, StatusType.FAILED.getValue());
    }

    if (response == null) {
      return new BreedImageResponse(null, StatusType.FAILED.getValue());
    }

    if (request.getSize() != -1) {
      int length = response.getMessage().size();
      while (length > 0 && length > request.getSize()) {
        response.getMessage().remove(length - 1);
        length -= 1;
      }
    }

    return response;
  }

  @Override
  public SubBreedResponse findSubBreed(SubBreedRequest request) {
    String result = "";
    try {
      result = handler.handleRequest(request.getUrl(), request.getConnectionTimeout(),
          request.getReadTimeout());
    } catch (IOException e) {
      log.error("[x] Failed to request: " + request);
      log.error("[x] With error: " + e.getMessage());
      return new SubBreedResponse(null, StatusType.FAILED.getValue());
    }

    if (result == "") {
      return new SubBreedResponse(null, StatusType.FAILED.getValue());
    }

    SubBreedResponse response;
    try {
      response = gson.fromJson(result, SubBreedResponse.class);
    } catch (JsonSyntaxException e) {
      log.error("[x] Failed to convert: " + result);
      log.error("[x] With error: " + e.getMessage());
      return new SubBreedResponse(null, StatusType.FAILED.getValue());
    }

    if (response == null) {
      return new SubBreedResponse(null, StatusType.FAILED.getValue());
    }

    return response;
  }
}
