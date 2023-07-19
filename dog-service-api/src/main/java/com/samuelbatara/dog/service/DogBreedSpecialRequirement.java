package com.samuelbatara.dog.service;

import com.samuelbatara.dog.model.BreedImageRequest;
import com.samuelbatara.dog.model.BreedImageResponse;

import java.util.List;
import java.util.Map;

public interface DogBreedSpecialRequirement {
  BreedImageResponse handleShiba(BreedImageRequest request);
  Map<String, List<String>> handleSheepdog(String key, List<String> value);
  Map<String, List<String>> handleTerrier(String key, List<String> value);
}
