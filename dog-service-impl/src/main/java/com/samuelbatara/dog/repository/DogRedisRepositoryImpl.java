package com.samuelbatara.dog.repository;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class DogRedisRepositoryImpl implements DogRedisRepository {
  private static final int TTL = 3600;
  private static final String KEY = "DOG_BREED";
  private static final String HASH_KEY = "ALL";
  private final RedisTemplate redisTemplate;
  private final int ttl;

  public DogRedisRepositoryImpl(RedisTemplate redisTemplate) {
    this(redisTemplate, TTL);
  }

  public DogRedisRepositoryImpl(RedisTemplate redisTemplate, int ttl) {
    this.redisTemplate = redisTemplate;
    this.ttl = ttl;
  }

  @Override
  public boolean put(String key, Object object) {
    redisTemplate.opsForHash().put(key, key, object);
    redisTemplate.opsForHash().getOperations().expire(key, ttl, TimeUnit.SECONDS);
    return true;
  }

  @Override
  public Object get(String key) {
    Object result = redisTemplate.opsForHash().get(key, key);
    return result;
  }
}
