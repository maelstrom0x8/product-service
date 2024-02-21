package com.marktplatz.productservice.domain.repository;

import com.marktplatz.productservice.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
