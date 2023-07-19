package com.samuelbatara.dog.controller;

import com.samuelbatara.dog.model.*;
import com.samuelbatara.dog.service.DogService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1")
public class DogControllerImpl implements DogController {
  private final DogService dogService;

  public DogControllerImpl(DogService dogService) {
    this.dogService = dogService;
  }

  @Override
  public ResponseEntity<AllBreedsResponse> getAllBreeds() {
    final int timeout = 5000;
    AllBreedsRequest request = new AllBreedsRequest();
    request.setConnectionTimeout(timeout);
    request.setReadTimeout(timeout);
    AllBreedsResponse response = dogService.getAllBreeds(request);

    if (response.getStatus().equals(StatusType.FAILED.getValue())) {
      return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok().body(response);
  }

  @Override
  public ResponseEntity<Object> getSubBreed(String breed) {
    final int timeout = 2000;
    SubBreedRequest request = new SubBreedRequest(breed, timeout, timeout);
    Object result = dogService.getSubBreed(request);

    if (breed.equals(DogBreed.SHIBA.getBreed())) {
      SubBreedResponse response = (SubBreedResponse) result;
      if (response.getStatus().equals(StatusType.FAILED.getValue())) {
        return ResponseEntity.badRequest().body(response);
      }

      return ResponseEntity.ok().body(response);
    } else if (breed.equals(DogBreed.SHEEPDOG.getBreed()) || breed.equals(DogBreed.TERRIER.getBreed())) {
      AllBreedsResponse response = (AllBreedsResponse) result;
      if (response.getStatus().equals(StatusType.FAILED.getValue())) {
        return ResponseEntity.badRequest().body(response);
      }

      return ResponseEntity.ok().body(response);
    }

    SubBreedResponse response = (SubBreedResponse) result;

    if (response.getStatus().equals(StatusType.FAILED.getValue())) {
      return ResponseEntity.badRequest().body(response);
    }

    return ResponseEntity.ok().body(response);
  }
}
