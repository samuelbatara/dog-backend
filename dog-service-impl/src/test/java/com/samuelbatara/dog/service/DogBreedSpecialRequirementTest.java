package com.samuelbatara.dog.service;

import com.samuelbatara.dog.model.BreedImageRequest;
import com.samuelbatara.dog.model.BreedImageResponse;
import com.samuelbatara.dog.model.StatusType;
import com.samuelbatara.dog.repository.DogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class DogBreedSpecialRequirementTest {
  @Mock
  private DogRepository dogRepository;
  private DogBreedSpecialRequirementImpl dogBreedSpecialRequirement;
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    dogBreedSpecialRequirement = new DogBreedSpecialRequirementImpl(dogRepository);
  }

  @Test
  public void testHandleShiba() {
    BreedImageResponse expectedResponse = new BreedImageResponse(
        List.of("image1.jpg", "image2.jpg", "image3.jpg"),
        StatusType.SUCCESS.getValue()
    );

    when(dogRepository.findBreedImages(any(BreedImageRequest.class)))
        .thenReturn(expectedResponse);

    BreedImageResponse actualResponse = dogBreedSpecialRequirement.handleShiba(
        new BreedImageRequest("shiba", 3));
    Assertions.assertNotNull(actualResponse);
  }

  @Test
  public void testHandleSheepdog() {
    String breed = "sheepdog";
    List<String> subBreeds = List.of("english", "indonesia");

    Map<String, List<String>> result = dogBreedSpecialRequirement.handleSheepdog(breed, subBreeds);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(subBreeds.size(), result.keySet().size());
    result.keySet().forEach(key -> Assertions.assertTrue(key.startsWith(breed)));
  }

  @Test
  public void testHandleTerrier() {
    String breed = "terrier";
    List<String> subBreeds = List.of("australia", "america");
    List<String> imageUrls = List.of("https://dog.api.com/breed/terrier-australia/image1.jpg",
                                     "https://dog.api.com/breed/terrier-america/image2.jpg",
                                     "https://dog.api.com/breed/terrier-australia/image3.jpg");

    when(dogRepository.findBreedImages(any(BreedImageRequest.class)))
        .thenReturn(new BreedImageResponse(imageUrls, StatusType.SUCCESS.getValue()));

    Map<String, List<String>> result = dogBreedSpecialRequirement.handleTerrier(breed, subBreeds);
    Assertions.assertNotNull(result);
    Assertions.assertEquals(subBreeds.size(), result.keySet().size());
    result.keySet().forEach(key -> Assertions.assertTrue(key.startsWith(breed)));
    subBreeds.stream().forEach(subBreed -> {
      result.get(breed + "-" + subBreed).stream()
          .forEach(url -> Assertions.assertTrue(url.contains(breed + "-" +  subBreed)));
    });
  }
}
