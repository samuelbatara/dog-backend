package com.samuelbatara.dog.service;

import com.samuelbatara.dog.model.*;
import com.samuelbatara.dog.repository.DogCacheRepositoryImpl;
import com.samuelbatara.dog.repository.DogRepository;

import java.util.List;
import java.util.Map;

public class DogServiceImpl implements DogService {

  private final DogRepository dogRepository;
  private final DogBreedSpecialRequirement  dogBreedSpecialRequirement;

  public DogServiceImpl(DogCacheRepositoryImpl dogRepository,
                        DogBreedSpecialRequirement dogBreedSpecialRequirement) {
    this.dogRepository = dogRepository;
    this.dogBreedSpecialRequirement = dogBreedSpecialRequirement;
  }

  @Override
  public AllBreedsResponse getAllBreeds(AllBreedsRequest request) {
    AllBreedsResponse response = dogRepository.findAllBreeds(request);

    // handle shiba
    if (response.getMessage().containsKey(DogBreed.SHIBA.getBreed())) {
      BreedImageResponse shibaImages = dogBreedSpecialRequirement.handleShiba(
          new BreedImageRequest(DogBreed.SHIBA.getBreed(), 5)
      );

      response.getMessage().put(DogBreed.SHIBA.getBreed(), shibaImages.getMessage());
    }

    // handle sheepdog
    if (response.getMessage().containsKey(DogBreed.SHEEPDOG.getBreed())) {
      Map<String, List<String>> map = dogBreedSpecialRequirement.handleSheepdog(
          DogBreed.SHEEPDOG.getBreed(), response.getMessage().get(DogBreed.SHEEPDOG.getBreed())
      );

      response.getMessage().remove(DogBreed.SHEEPDOG.getBreed());
      response.getMessage().putAll(map);
    }

    // handle terrier
    if (response.getMessage().containsKey(DogBreed.TERRIER.getBreed())) {
      Map<String, List<String>> map = dogBreedSpecialRequirement.handleTerrier(
          DogBreed.TERRIER.getBreed(), response.getMessage().get(DogBreed.TERRIER.getBreed())
      );

      response.getMessage().remove(DogBreed.TERRIER.getBreed());
      response.getMessage().putAll(map);
    }

    return response;
  }

  @Override
  public Object getSubBreed(SubBreedRequest request) {
    SubBreedResponse response = dogRepository.findSubBreed(request);

    if (response.getStatus().equals(StatusType.FAILED.getValue())) {
      return new SubBreedResponse(null, StatusType.FAILED.getValue());
    }

    if (request.getBreed().equals(DogBreed.SHIBA.getBreed())) {
      BreedImageResponse shibaResponse = dogBreedSpecialRequirement.handleShiba(
          new BreedImageRequest(request.getBreed(), 5));

      if (shibaResponse.getStatus().equals(StatusType.FAILED.getValue())) {
        return new SubBreedResponse(null, StatusType.FAILED.getValue());
      }

      response.setMessage(shibaResponse.getMessage());
      return response;
    } else if (request.getBreed().equals(DogBreed.SHEEPDOG.getBreed())) {
      Map<String, List<String>> message = dogBreedSpecialRequirement.handleSheepdog(
          request.getBreed(), response.getMessage()
      );

      if (message == null) {
        return new AllBreedsResponse(null, StatusType.FAILED.getValue());
      }

      return new AllBreedsResponse(
          message, StatusType.SUCCESS.getValue()
      );
    } else if (request.getBreed().equals(DogBreed.TERRIER.getBreed())) {
      Map<String, List<String>> message = dogBreedSpecialRequirement.handleTerrier(
          request.getBreed(), response.getMessage()
      );

      if (message == null) {
        return new AllBreedsResponse(null, StatusType.FAILED.getValue());
      }

      return new AllBreedsResponse(
          message, StatusType.SUCCESS.getValue()
      );
    }

    return response;
  }
}
