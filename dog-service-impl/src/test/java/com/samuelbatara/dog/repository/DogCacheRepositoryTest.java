package com.samuelbatara.dog.repository;

import com.samuelbatara.dog.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DogCacheRepositoryTest {
  @Mock
  private DogRepositoryImpl dogRepository;
  @Mock
  private DogRedisRepository dogRedisRepository;
  private DogCacheRepositoryImpl dogCacheRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.dogCacheRepository = new DogCacheRepositoryImpl(dogRepository, dogRedisRepository);
  }

  @Test
  public void testFindAllBreedsFromRepository() {
    AllBreedsResponse expectedResponse = new AllBreedsResponse(null, StatusType.SUCCESS.getValue());

    when(dogRedisRepository.get(anyString()))
        .thenReturn(null);
    when(dogRedisRepository.put(anyString(), any(Map.class)))
        .thenReturn(true);
    when(dogRepository.findAllBreeds(any(AllBreedsRequest.class)))
        .thenReturn(expectedResponse);

    AllBreedsResponse actualResponse = dogCacheRepository.findAllBreeds(new AllBreedsRequest());
    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
  }

  @Test
  public void testFindAllBreedsFromCache() {
    Map<String, List<String>> message = new HashMap<>();
    message.put("sheepdog", List.of("english", "america"));

    when(dogRedisRepository.get(anyString()))
        .thenReturn(message);

    AllBreedsResponse actualResponse = dogCacheRepository.findAllBreeds(new AllBreedsRequest());
    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(actualResponse.getMessage(), message);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
  }

  @Test
  public void testFindBreedImagesFromRepository() {
    BreedImageResponse expectedResponse = new BreedImageResponse(null, StatusType.SUCCESS.getValue());

    when(dogRedisRepository.get(anyString()))
        .thenReturn(null);
    when(dogRedisRepository.put(anyString(), any(List.class)))
        .thenReturn(true);
    when(dogRepository.findBreedImages(any(BreedImageRequest.class)))
        .thenReturn(expectedResponse);

    BreedImageResponse actualResponse = dogCacheRepository.findBreedImages(
        new BreedImageRequest("sheepdog", 5));
    Assertions.assertNotNull(actualResponse);
  }

  @Test
  public void testFindBreedImagesFromCache() {
    List<String> imageUrls = List.of("image1.jpg", "image2.jpg", "image3.jpg");

    when(dogRedisRepository.get(anyString()))
        .thenReturn(imageUrls);

    BreedImageResponse actualResponse = dogCacheRepository.findBreedImages(
        new BreedImageRequest("shiba", 3));
    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(imageUrls, actualResponse.getMessage());
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
  }

  @Test
  public void testFindSubBreedFromRepository() {
    SubBreedResponse expectedResponse = new SubBreedResponse(null, StatusType.SUCCESS.getValue());

    when(dogRedisRepository.get(anyString()))
        .thenReturn(null);
    when(dogRedisRepository.put(anyString(), any(List.class)))
        .thenReturn(true);
    when(dogRepository.findSubBreed(any(SubBreedRequest.class)))
        .thenReturn(expectedResponse);

    SubBreedResponse actualResponse = dogCacheRepository.findSubBreed(
        new SubBreedRequest("shiba", 2000, 2000));
    Assertions.assertNotNull(actualResponse);
  }

  @Test
  public void testFindSubBreedFromCache() {
    List<String> subBreeds = List.of("english", "indonesia");

    when(dogRedisRepository.get(anyString()))
        .thenReturn(subBreeds);

    SubBreedResponse actualResponse = dogCacheRepository.findSubBreed(
        new SubBreedRequest("sheepdog", 2000, 2000));
    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(subBreeds, actualResponse.getMessage());
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
  }
}
