
CREATE TABLE images (
    product_id BIGINT NOT NULL,
    url VARCHAR(200) NOT NULL UNIQUE,
    FOREIGN KEY (product_id) REFERENCES products(product_id) ON DELETE CASCADE
);
