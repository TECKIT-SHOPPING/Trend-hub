package com.trendhub.trendhub.domain.product.repository;

import com.trendhub.trendhub.domain.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


}
