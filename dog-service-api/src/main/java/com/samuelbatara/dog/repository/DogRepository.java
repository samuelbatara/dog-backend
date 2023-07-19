package com.samuelbatara.dog.repository;

import com.samuelbatara.dog.model.*;

public interface DogRepository {
  AllBreedsResponse findAllBreeds(AllBreedsRequest request);
  BreedImageResponse findBreedImages(BreedImageRequest request);
  SubBreedResponse findSubBreed(SubBreedRequest request);
}
