package com.samuelbatara.dog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

  @Bean
  public JedisConnectionFactory jedisConnectionFactory(@Value(value = "${redis.hostname}") String hostname,
                                                       @Value(value = "${redis.port}") int port,
                                                       @Value(value = "${redis.username}") String username,
                                                       @Value(value = "${redis.password}") String password,
                                                       @Value(value = "${redis.database}") int database) {
    RedisStandaloneConfiguration redisConfiguration = new RedisStandaloneConfiguration();
    redisConfiguration.setHostName(hostname);
    redisConfiguration.setPort(port);
    redisConfiguration.setUsername(username);
    redisConfiguration.setPassword(password);
    redisConfiguration.setDatabase(database);
    JedisConnectionFactory factory = new JedisConnectionFactory(redisConfiguration);
    return factory;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory factory) {
    RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(factory);
    return redisTemplate;
  }
}
