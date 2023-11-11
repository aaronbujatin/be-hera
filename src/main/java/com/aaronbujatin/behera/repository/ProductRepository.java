
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop10ByOrderByDateCreatedDesc();

    List<Product> findByCategoryAndBrandIn(String category, List<String> brands);

    List<Product> findByCategory(String category);

}

