
package com.aaronbujatin.behera.service;

import com.aaronbujatin.behera.entity.Product;

import java.util.List;

public interface ProductService {

    Product saveProduct(Product product);

    Product getProductById(Long id);

    List<Product> getAllProduct();

    Product updateProduct(Product product);

    void deleteProductById(Long id);

    List<Product> getFiveRecentAddedProduct();

    List<Product> getProductsByCategoryAndBrands(String category, List<String> brands);

    List<Product> getProductsByCategory(String category);


}
