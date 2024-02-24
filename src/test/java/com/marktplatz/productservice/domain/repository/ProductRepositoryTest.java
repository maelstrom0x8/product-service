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
package com.marktplatz.productservice.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.marktplatz.productservice.domain.model.Product;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

@DataJpaTest(properties = "{spring.datasource.url=jdbc:tc:postgresql:15:///test}")
@Testcontainers
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:db/products.sql")
public class ProductRepositoryTest {

  @Autowired private ProductRepository productRepository;

  @Test
  void initialProductCount() {
    long count = productRepository.count();
    assertThat(count).isEqualTo(8);
  }

  @Test
  public void productLastModifiedTimestampUpdate() {
    Product product = productRepository.findById(403L).orElseThrow();
    LocalDateTime lastModified = product.getLastModified();

    product.setName("Sony HDRXC");
    productRepository.saveAndFlush(product);

    assertThat(product).isNotNull();
    assertThat(product.getLastModified()).isAfter(lastModified);
  }

  @Test
  public void canAddUniqueImageUrlsToProduct() {
    Set<String> urls = new HashSet<>();
    urls.add("https://image_one");
    urls.add("https://image_two");

    Product product = productRepository.findById(402L).orElseThrow();

    product.setImageUrls(urls);
    productRepository.saveAndFlush(product);

    Set<String> savedUrls = product.getImageUrls();
    assertThat(savedUrls).containsAll(urls);
  }
}
