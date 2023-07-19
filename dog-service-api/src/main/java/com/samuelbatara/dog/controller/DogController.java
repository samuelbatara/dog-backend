package com.samuelbatara.dog.controller;

import com.samuelbatara.dog.model.AllBreedsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DogController {
  @GetMapping(path = "/breeds/list/all")
  ResponseEntity<AllBreedsResponse> getAllBreeds();

  @GetMapping(path = "/breed/{breed}/list")
  ResponseEntity<Object> getSubBreed(@PathVariable String breed);
}
