package com.samuelbatara.dog.config;

import com.samuelbatara.dog.handler.HttpRequestHandler;
import com.samuelbatara.dog.handler.HttpRequestHandlerImpl;
import com.samuelbatara.dog.repository.*;
import com.samuelbatara.dog.service.DogBreedSpecialRequirement;
import com.samuelbatara.dog.service.DogBreedSpecialRequirementImpl;
import com.samuelbatara.dog.service.DogService;
import com.samuelbatara.dog.service.DogServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class DogConfiguration {
  @Bean
  public HttpRequestHandler httpRequestHandler() {
    return new HttpRequestHandlerImpl();
  }

  @Bean
  public DogRepositoryImpl dogRepository(HttpRequestHandler httpRequestHandler) {
    return new DogRepositoryImpl(httpRequestHandler);
  }

  @Bean
  public DogBreedSpecialRequirement dogBreedSpecialRequirement(DogRepository dogRepository) {
    return new DogBreedSpecialRequirementImpl(dogRepository);
  }

  @Bean
  public DogRedisRepository dogRedisRepository(RedisTemplate redisTemplate) {
    return new DogRedisRepositoryImpl(redisTemplate);
  }
  @Bean
  public DogCacheRepositoryImpl dogCacheRepository(DogRepositoryImpl dogRepository,
                                                   DogRedisRepository dogRedisRepository) {
    return new DogCacheRepositoryImpl(dogRepository, dogRedisRepository);
  }

  @Bean
  public DogService dogService(DogCacheRepositoryImpl dogCacheRepository, DogBreedSpecialRequirement dogBreedSpecialRequirement) {
    return new DogServiceImpl(dogCacheRepository, dogBreedSpecialRequirement);
  }
}
