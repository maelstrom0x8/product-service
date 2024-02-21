/*
 * Copyright (C) 2024 Emmanuel Godwin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.marktplatz.productservice.config;

import java.time.Duration;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
public class CacheConfiguration {

  @Value("${spring.data.redis.port}")
  private int REDIS_PORT;

  @Value("${spring.data.redis.host}")
  private String REDIS_HOST;

  @Bean
  LettuceConnectionFactory lettuceConnectionFactory() {
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT));
  }

  @Bean
  CacheManager cacheManager(LettuceConnectionFactory factory) {
    Set<String> cacheNames = Set.of("products", "stocks");
    RedisCacheConfiguration config =
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(8))
            .disableCachingNullValues();

    return RedisCacheManager.builder()
        .cacheDefaults(config)
        .initialCacheNames(cacheNames)
        .cacheWriter(RedisCacheWriter.nonLockingRedisCacheWriter(factory))
        .build();
  }
}
