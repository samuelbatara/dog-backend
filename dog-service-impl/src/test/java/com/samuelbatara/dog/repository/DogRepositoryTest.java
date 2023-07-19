package com.samuelbatara.dog.repository;

import com.google.gson.Gson;
import com.samuelbatara.dog.TestUtil;
import com.samuelbatara.dog.handler.HttpRequestHandler;
import com.samuelbatara.dog.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class DogRepositoryTest {
  @Mock
  private HttpRequestHandler handler;
  private DogRepository dogRepository;
  private Gson gson;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    dogRepository = new DogRepositoryImpl(handler);
    gson = new Gson();
  }

  @Test
  public void testFindAllBreeds() throws IOException {
    final String url = "https://dog.ceo/api/breeds/list/all";
    final int timeout = 5000;
    final AllBreedsRequest request = new AllBreedsRequest(url, timeout, timeout);
    final String mockResult = TestUtil.readResource("mock-all-breeds.json");

    when(handler.handleRequest(anyString(), anyInt(), anyInt()))
        .thenReturn(mockResult);

    AllBreedsResponse actualResponse = dogRepository.findAllBreeds(request);
    Assertions.assertNotNull(actualResponse);
  }

  @Test
  public void testFindBreedImages() throws IOException {
    final String breed = "hound";
    final int size = 5;
    final BreedImageRequest request = new BreedImageRequest(breed, size);
    String mockResult = TestUtil.readResource("mock-hound-images.json");

    when(handler.handleRequest(request.getUrl()))
        .thenReturn(mockResult);

    BreedImageResponse actualResponse = dogRepository.findBreedImages(request);

    Assertions.assertNotNull(actualResponse);
    Assertions.assertEquals(StatusType.SUCCESS.getValue(), actualResponse.getStatus());
    Assertions.assertEquals(request.getSize(), actualResponse.getMessage().size());
  }

  @Test
  public void testFindSubBreed() throws IOException {
    final String breed = "hound";
    final int timeout = 2000;
    final SubBreedRequest request = new SubBreedRequest(breed, timeout, timeout);
    final String mockResult = TestUtil.readResource("mock-hound-list.json");

    when(handler.handleRequest(anyString(), anyInt(), anyInt()))
        .thenReturn(mockResult);

    SubBreedResponse actualResponse = dogRepository.findSubBreed(request);
    Assertions.assertNotNull(actualResponse);
  }
}
