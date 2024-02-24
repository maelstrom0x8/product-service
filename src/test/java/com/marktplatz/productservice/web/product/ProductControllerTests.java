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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marktplatz.productservice.domain.ProductNotFoundException;
import com.marktplatz.productservice.domain.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {ProductController.class})
public class ProductControllerTests {

  @Autowired private MockMvc mockMvc;

  @MockBean private ProductService productService;

  @Test
  void fetchProductWithInvalidId() throws Exception {
    when(productService.getProductById(32L)).thenThrow(ProductNotFoundException.class);

    mockMvc
        .perform(get("/api/v1/products/32"))
        .andExpect(status().isNotFound())
        .andExpect(
            e -> assertThat(e.getResolvedException()).isInstanceOf(ProductNotFoundException.class))
        .andExpect(
            e -> assertThat("Product does not exist").isEqualTo(e.getResponse().getErrorMessage()));
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
