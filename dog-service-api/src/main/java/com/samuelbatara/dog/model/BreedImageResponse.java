package com.samuelbatara.dog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BreedImageResponse {
  private List<String> message;
  private String status;
}
