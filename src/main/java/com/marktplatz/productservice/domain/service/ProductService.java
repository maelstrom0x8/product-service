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
package com.marktplatz.productservice.domain.service;

import com.marktplatz.productservice.config.ApplicationProperties;
import com.marktplatz.productservice.domain.ProductNotFoundException;
import com.marktplatz.productservice.domain.model.Product;
import com.marktplatz.productservice.domain.repository.ProductRepository;
import com.marktplatz.productservice.web.product.ProductRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

  private final ProductRepository productRepository;
  private final ApplicationProperties properties;

  public ProductService(ProductRepository productRepository, ApplicationProperties properties) {
    this.productRepository = productRepository;
    this.properties = properties;
  }

  public List<Product> getAllProducts() {
    return productRepository.findAll();
  }

  public Product getProductById(Long id) {
    return productRepository.findById(id).orElseThrow(ProductNotFoundException::new);
  }

  public Product save(String name, String description) {
    Product product = new Product(name, description);
    return productRepository.save(product);
  }

  public void addAll(List<Product> products) {
    productRepository.saveAll(products);
  }

  public void deleteProductById(Long id) {
    productRepository.deleteById(id);
  }

  public List<Product> saveAll(List<ProductRequest> products) {
    return productRepository.saveAll(
        products.stream()
            .map(e -> new Product(e.name(), e.description()))
            .collect(Collectors.toList()));
  }

  public Product addImages(Long productId, Collection<String> urls)
      throws ProductNotFoundException {
    Product product =
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    product.getImageUrls().addAll(urls);
    return productRepository.save(product);
  }
}
