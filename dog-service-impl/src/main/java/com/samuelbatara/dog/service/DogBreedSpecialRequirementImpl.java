package com.samuelbatara.dog.service;

import com.samuelbatara.dog.model.BreedImageRequest;
import com.samuelbatara.dog.model.BreedImageResponse;
import com.samuelbatara.dog.model.StatusType;
import com.samuelbatara.dog.repository.DogRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DogBreedSpecialRequirementImpl implements DogBreedSpecialRequirement {
  private final DogRepository dogRepository;

  public DogBreedSpecialRequirementImpl(DogRepository dogRepository) {
    this.dogRepository = dogRepository;
  }

  @Override
  public BreedImageResponse handleShiba(BreedImageRequest request) {
    return dogRepository.findBreedImages(request);
  }

  @Override
  public Map<String, List<String>> handleSheepdog(String breed, List<String> subBreeds) {
    Map<String, List<String>> mapBreedToSubBreed = new HashMap<>();
    subBreeds.stream().forEach(subBreed -> {
      mapBreedToSubBreed.put(breed + "-" + subBreed, new ArrayList<>());
    });

    return mapBreedToSubBreed;
  }

  @Override
  public Map<String, List<String>> handleTerrier(String breed, List<String> subBreeds) {
    BreedImageRequest request = new BreedImageRequest(breed, -1);
    BreedImageResponse response = dogRepository.findBreedImages(request);

    if (response.getStatus().equals(StatusType.FAILED.getValue())) {
      return null;
    }

    Map<String, List<String>> mapBreedToImages = new HashMap<>();
    subBreeds.stream().forEach(subBreed -> {
      mapBreedToImages.put(breed + "-" + subBreed, new ArrayList<>());
    });

    response.getMessage().stream().forEach(imageUrl -> {
      String[] parts = imageUrl.split("/");
      String key = parts[parts.length - 2];

      if (mapBreedToImages.containsKey(key)) {
        mapBreedToImages.get(key).add(imageUrl);
      } else {
        // TODO: inconsistent data
      }
    });

    return mapBreedToImages;
  }
}
