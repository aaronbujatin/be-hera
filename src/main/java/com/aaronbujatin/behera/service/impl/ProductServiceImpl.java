
package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product saveProduct(Product product) {
        product.setDateCreated(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new InvalidArgumentException("Product id :" + id + " was not found!"));
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> getFiveRecentAddedProduct() {
        return productRepository.findTop10ByOrderByDateCreatedDesc();
    }

    @Override
    public List<Product> getProductsByCategoryAndBrands(String category, List<String> brands) {
        return productRepository.findByCategoryAndBrandIn(category, brands);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand() {
        return null;
    }

    @Override
    public List<Product> getProductsByCategoryAndPriceBetween(String category, double minPrice, double maxPrice) {
        return productRepository.findByCategoryAndPriceBetween(category, minPrice, maxPrice);
    }

    @Override
    public Map<String, Long> getBrandsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        // Count occurrences of each brand using groupingBy
        Map<String, Long> brandQuantities = products.stream()
                .collect(Collectors.groupingBy(Product::getBrand, Collectors.counting()));

        return brandQuantities;
    }
}
