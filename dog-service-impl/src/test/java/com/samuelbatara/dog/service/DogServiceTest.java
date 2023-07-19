package com.samuelbatara.dog.service;

import com.google.gson.Gson;
import com.samuelbatara.dog.TestUtil;
import com.samuelbatara.dog.model.*;
import com.samuelbatara.dog.repository.DogCacheRepositoryImpl;
import com.samuelbatara.dog.repository.DogRepository;
import com.samuelbatara.dog.repository.DogRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

public class DogServiceTest {
  @Mock
  private DogCacheRepositoryImpl dogCacheRepository;
  @Mock
  private DogBreedSpecialRequirement dogBreedSpecialRequirement;
  private DogServiceImpl dogService;
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    dogService = new DogServiceImpl(dogCacheRepository, dogBreedSpecialRequirement);
  }

  @Test
  public void testGetAllBreeds() throws IOException {
    AllBreedsResponse allBreedsResponse = (AllBreedsResponse) TestUtil.readResource(
        "mock-all-breeds.json", AllBreedsResponse.class);

    BreedImageResponse shibaImageUrls = (BreedImageResponse) TestUtil.readResource(
        "mock-shiba-images.json", BreedImageResponse.class);

    List<String> sheepdogSubBreeds = List.of("english", "america");
    Map<String, List<String>> sheepdogMap = new HashMap<>();
    sheepdogSubBreeds.stream()
        .forEach(subBreed -> sheepdogMap.put("sheepdog-" + subBreed, new ArrayList<>()));

    Map<String,  List<String>> terrierMap = new HashMap<>();
    terrierMap.put("terrier-english", List.of("https://dog.api.com/breed/terrier-english/image1.jpg"));
    terrierMap.put("terrier-america", List.of("https://dog.api.com/breed/terrier-america/image2.jpg"));

    when(dogCacheRepository.findAllBreeds(any(AllBreedsRequest.class)))
        .thenReturn(allBreedsResponse);
    when(dogBreedSpecialRequirement.handleShiba(any(BreedImageRequest.class)))
        .thenReturn(shibaImageUrls);
    when(dogBreedSpecialRequirement.handleSheepdog(anyString(), any(List.class)))
        .thenReturn(sheepdogMap);
    when(dogBreedSpecialRequirement.handleTerrier(anyString(), any(List.class)))
        .thenReturn(terrierMap);

    AllBreedsResponse response = dogService.getAllBreeds(new AllBreedsRequest());
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), response.getStatus());
    Assertions.assertTrue(response.getMessage().containsKey("shiba"));
    Assertions.assertTrue(response.getMessage().keySet().stream().anyMatch(s -> s.startsWith("sheepdog")));
    Assertions.assertTrue(response.getMessage().keySet().stream().anyMatch(s -> s.startsWith("terrier")));
  }

  @Test
  public void testGetSubBreedTerrier() throws IOException {
    SubBreedResponse terrierSubBreeds = (SubBreedResponse) TestUtil.readResource(
        "mock-terrier-list.json", SubBreedResponse.class);

    BreedImageResponse terrierImageUrls = (BreedImageResponse) TestUtil.readResource(
        "mock-terrier-images.json", BreedImageResponse.class);
    Map<String, List<String>> terrierMap = new HashMap<>();
    terrierImageUrls.getMessage().stream().forEach(url -> {
      String[] parts = url.split("/");
      terrierMap.put(parts[parts.length-2], new ArrayList<>());
    });
    terrierImageUrls.getMessage().stream().forEach(url -> {
      String[] parts = url.split("/");
      terrierMap.get(parts[parts.length-2]).add(url);
    });

    when(dogCacheRepository.findSubBreed(any(SubBreedRequest.class)))
        .thenReturn(terrierSubBreeds);
    when(dogBreedSpecialRequirement.handleTerrier(anyString(), any(List.class)))
        .thenReturn(terrierMap);

    AllBreedsResponse actualResponse = (AllBreedsResponse) dogService.getSubBreed(
        new SubBreedRequest("terrier", 2000, 2000));
    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
    Assertions.assertEquals(terrierMap, actualResponse.getMessage());
  }
}
