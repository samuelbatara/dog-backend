package com.samuelbatara.dog;

import com.google.gson.Gson;
import com.samuelbatara.dog.repository.DogRepositoryTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TestUtil {

  public static String readResource(String filename) throws IOException {
    InputStream inputStream = DogRepositoryTest.class.getClassLoader()
        .getResourceAsStream(filename);
    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
    StringBuilder stringBuilder = new StringBuilder();
    String line;
    while ( (line = bufferedReader.readLine()) != null) {
      stringBuilder.append(line);
    }
    return stringBuilder.toString();
  }

  public static Object readResource(String filename, Class<?> clazz) throws IOException {
    final Gson gson = new Gson();
    return gson.fromJson(readResource(filename), clazz);
  }
}
