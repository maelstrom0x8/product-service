package com.marktplatz.productservice.domain.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@ActiveProfiles({"flyway"})
@Sql("classpath:db/products.sql")
public class ProductRepositoryTest {

    @Autowired private ProductRepository productRepository;

    @Test
    void initialProductCount() {

    }
}
