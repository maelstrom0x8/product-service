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
package com.marktplatz.productservice.web.product;

import com.marktplatz.productservice.domain.ProductNotFoundException;
import com.marktplatz.productservice.domain.model.Product;
import com.marktplatz.productservice.domain.service.ProductService;
import java.util.Collection;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public List<Product> fetchAll() {
    return productService.getAllProducts();
  }

  @GetMapping("/{productId}")
  public Product fetchById(@PathVariable Long productId) {
    return productService.getProductById(productId);
  }

  @PostMapping("/new")
  public Product post(ProductRequest product) {
    return productService.save(product.name(), product.description());
  }

  @DeleteMapping("/{productId}")
  public void deleteProduct(Long productId) {
    productService.deleteProductById(productId);
  }

  @PostMapping("/{productId}")
  public Product addImageUrls(@PathVariable Long productId, @RequestBody Collection<String> urls)
      throws ProductNotFoundException {
    return productService.addImages(productId, urls);
  }
}
