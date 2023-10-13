package com.aaronbujatin.behera.repository;

import com.aaronbujatin.practicethree.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
