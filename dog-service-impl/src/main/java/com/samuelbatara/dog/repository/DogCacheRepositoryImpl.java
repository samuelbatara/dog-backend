package com.samuelbatara.dog.repository;

import com.samuelbatara.dog.model.AllBreedsRequest;
import com.samuelbatara.dog.model.AllBreedsResponse;
import com.samuelbatara.dog.model.BreedImageRequest;
import com.samuelbatara.dog.model.BreedImageResponse;
import com.samuelbatara.dog.model.StatusType;
import com.samuelbatara.dog.model.SubBreedRequest;
import com.samuelbatara.dog.model.SubBreedResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
public class DogCacheRepositoryImpl implements DogRepository {
  private static final String ALL_BREEDS = "BREEDS.ALL";
  private static final String BREED_URLS = "BREED.IMAGE_URLS.";
  private static final String BREED_SUB = "BREED.SUB_BREED.";

  private final DogRepository repository;
  private final  DogRedisRepository redisRepository;

  public DogCacheRepositoryImpl(DogRepositoryImpl repository,
                                DogRedisRepository redisRepository) {
    this.repository = repository;
    this.redisRepository = redisRepository;
  }

  private String generateKeyForAllBreeds() {
    return ALL_BREEDS;
  }

  private String generateKeyForBreedUrls(String breed) {
    return BREED_URLS + breed;
  }

  private String generateKeyForBreedSub(String breed) {
    return BREED_SUB + breed;
  }

  @Override
  public AllBreedsResponse findAllBreeds(AllBreedsRequest request) {
    log.info("[x] findAllBreeds");
    String key = generateKeyForAllBreeds();
    Object result = redisRepository.get(key);
    if (result != null) {
      Map<String, List<String>> message = (Map<String, List<String>>) result;
      log.info("[x] retrieve from cache");
      return new AllBreedsResponse(message, StatusType.SUCCESS.getValue());
    }

    log.info("[x] retrieve from repository");
    AllBreedsResponse response = repository.findAllBreeds(request);
    redisRepository.put(key, response.getMessage());
    return response;
  }

  @Override
  public BreedImageResponse findBreedImages(BreedImageRequest request) {
    log.info("[x] findBreedImages");
    String key = generateKeyForBreedUrls(request.getBreed());
    Object result = redisRepository.get(key);
    if (result != null) {
      log.info("[x] retrieve from cache");
      List<String> message = (List<String>) result;
      return new BreedImageResponse(message, StatusType.SUCCESS.getValue());
    }

    log.info("[x] retrieve from repository");
    BreedImageResponse response = repository.findBreedImages(request);
    redisRepository.put(key, response.getMessage());
    return response;
  }

  @Override
  public SubBreedResponse findSubBreed(SubBreedRequest request) {
    log.info("[x] findSubBreed");
    String key = generateKeyForBreedSub(request.getBreed());
    Object result = redisRepository.get(key);
    if (result != null) {
      log.info("[x] retrieve from cache");
      List<String> message = (List<String>) result;
      return new SubBreedResponse(message, StatusType.SUCCESS.getValue());
    }

    log.info("[x] retrieve from repository");
    SubBreedResponse response = repository.findSubBreed(request);
    redisRepository.put(key, response.getMessage());
    return response;
  }
}
