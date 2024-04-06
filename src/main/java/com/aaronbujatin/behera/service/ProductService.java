
package com.aaronbujatin.behera.service;

import com.aaronbujatin.behera.dto.ProductDto;
import com.aaronbujatin.behera.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductById(Long id);

//    List<Product> getAllProduct();

    ProductDto updateProduct(ProductDto product);

    void deleteProductById(Long id);

    List<Product> getFiveRecentAddedProduct();

    List<Product> getProductsByCategoryAndBrands(String category, List<String> brands);

    List<Product> getProductsByCategory(String category);

    List<Product> getProductsByCategoryAndBrand();

    List<Product> getProductsByCategoryAndPriceBetween(String category, double minPrice, double maxPrice);

    Map<String, Long> getBrandsByCategory(String category);

    Optional<Double> getMaxPriceByCategory(String category);

    Page<Product> getProductsByFilter(Double minPrice,
                                      Double maxPrice,
                                      String brand,
                                      String category,
                                      String sortBy,
                                      String sortDirection,
                                      Pageable pageable);

    Page<Product> getAllProduct(Pageable pageable);


}
