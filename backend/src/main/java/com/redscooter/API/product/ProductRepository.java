package com.redscooter.API.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, com.redscooter.API.product.ProductDynamicQueryRepository {
    Product findProductById(Long id);
}
