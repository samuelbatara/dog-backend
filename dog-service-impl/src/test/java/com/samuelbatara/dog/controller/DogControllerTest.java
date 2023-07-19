package com.samuelbatara.dog.controller;

import com.samuelbatara.dog.model.AllBreedsResponse;
import com.samuelbatara.dog.model.StatusType;
import com.samuelbatara.dog.model.SubBreedResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.util.List;

public class DogControllerTest {
  private TestRestTemplate template;
  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    template = new TestRestTemplate();
  }

//  @Test
  public void testGetAllBreeds() {
    String url = "http://localhost:9000/api/v1/breeds/list/all";
    AllBreedsResponse response = template.getForObject(url, AllBreedsResponse.class);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), response.getStatus());
    Assertions.assertTrue(response.getMessage().size() > 0);
    List.of("shiba", "sheepdog", "terrier").stream().forEach(breed -> {
      Assertions.assertTrue(response.getMessage().keySet().stream().anyMatch(key -> key.startsWith(breed)));
    });
  }

//  @Test
  public void testGetShibaSubBreed() {
    String url = "http://localhost:9000/api/v1/breed/shiba/list";
    SubBreedResponse response = template.getForObject(url, SubBreedResponse.class);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), response.getStatus());
    Assertions.assertTrue(response.getMessage().size() > 0);
  }

//  @Test
  public void testGetSheepdogSubBreed() {
    String url = "http://localhost:9000/api/v1/breed/sheepdog/list";
    AllBreedsResponse response = template.getForObject(url, AllBreedsResponse.class);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), response.getStatus());
    Assertions.assertTrue(response.getMessage().keySet().size() > 0);
    Assertions.assertTrue(response.getMessage().keySet().stream().allMatch(key -> key.startsWith("sheepdog")));
  }

//  @Test
  public void testGetTerrierSubBreed() {
    String url = "http://localhost:9000/api/v1/breed/terrier/list";
    AllBreedsResponse response = template.getForObject(url, AllBreedsResponse.class);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), response.getStatus());
    Assertions.assertTrue(response.getMessage().keySet().size() > 0);
    Assertions.assertTrue(response.getMessage().keySet().stream().allMatch(key -> key.startsWith("terrier")));
  }

//  @Test
  public void testGetSubBreed_withNotFoundBreed() {
    String url = "http://localhost:9000/api/v1/breed/mopik/list";
    SubBreedResponse response = template.getForObject(url, SubBreedResponse.class);
    Assertions.assertNotNull(response);
    Assertions.assertEquals(StatusType.FAILED.getValue(), response.getStatus());
    Assertions.assertNull(response.getMessage());
  }
}
