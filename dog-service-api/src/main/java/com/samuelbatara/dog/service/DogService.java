package com.samuelbatara.dog.service;

import com.samuelbatara.dog.model.*;

public interface DogService {
  AllBreedsResponse getAllBreeds(AllBreedsRequest request);
  Object getSubBreed(SubBreedRequest request);
}
