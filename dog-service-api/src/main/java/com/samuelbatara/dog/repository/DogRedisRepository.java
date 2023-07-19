package com.samuelbatara.dog.repository;

public interface DogRedisRepository {
  boolean put(String key, Object object);
  Object get(String key);
}
