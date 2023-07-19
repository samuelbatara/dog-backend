package com.samuelbatara.dog.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllBreedsResponse {
  private Map<String, List<String>> message;
  private String status;
}
