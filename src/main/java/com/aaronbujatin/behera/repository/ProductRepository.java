
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop10ByOrderByDateCreatedDesc();

    List<Product> findByCategoryAndBrandIn(String category, List<String> brands);

    List<Product> findByCategory(String category);

    List<Product> findByCategoryAndBrand(String category, String brand);

    List<Product> findByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);

    @Query("SELECT MAX(p.price) FROM Product p WHERE p.category = ?1")
    Optional<Double> findMaxPriceByCategory(String category);

    @Query("SELECT p FROM Product p WHERE " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
            "(:brand IS NULL OR p.brand = :brand) AND " +
            "(:category IS NULL OR p.category = :category) " +
            "ORDER BY " +
            "CASE WHEN :sortBy = 'price' AND :sortDirection = 'ASC' THEN p.price END ASC, " +
            "CASE WHEN :sortBy = 'price' AND :sortDirection = 'DESC' THEN p.price END DESC, " +
            "CASE WHEN :sortBy = 'name' AND :sortDirection = 'ASC' THEN p.name END ASC, " +
            "CASE WHEN :sortBy = 'name' AND :sortDirection = 'DESC' THEN p.name END DESC")
    Page<Product> findProductsByFilters(
            @Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice,
            @Param("brand") String brand, @Param("category") String category,
            @Param("sortBy") String sortBy, @Param("sortDirection") String sortDirection, Pageable pageable);


}

