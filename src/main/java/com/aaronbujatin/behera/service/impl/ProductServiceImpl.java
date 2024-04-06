
package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.dto.ProductDto;
import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.exception.ResourceNotFoundException;
import com.aaronbujatin.behera.mapper.ProductDtoMapper;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Override
    public Product saveProduct(Product product) {
        product.setDateCreated(LocalDate.now());
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new InvalidArgumentException("Product id :" + id + " was not found!"));
    }

//    @Override
//    public List<Product> getAllProduct() {
//        return productRepository.findAll();
//    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {

        Product product = productRepository.findById(productDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product id " + productDto.getId() +  " was not found"));

        if (product != null) {
//            ProductDto productDto = new ProductDto();
            product.setName(productDto.getName());
            product.setPrice(productDto.getPrice());
            product.setImageUrl(productDto.getImageUrl());
            product.setDescriptions(productDto.getDescriptions());
            product.setDateCreated(productDto.getDateCreated());
            product.setStock(productDto.getStock());
            product.setBrand(productDto.getBrand());
            product.setCategory(productDto.getCategory());
            productRepository.save(product);
        }
        return productDtoMapper.apply(product);
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

    @Override
    public Optional<Double> getMaxPriceByCategory(String category) {
        return productRepository.findMaxPriceByCategory(category);
    }

    @Override
    public Page<Product> getProductsByFilter(Double minPrice, Double maxPrice,
                                             String brand, String category,
                                             String sortBy, String sortDirection,
                                             Pageable pageable) {
        return productRepository.findProductsByFilters(
                minPrice, maxPrice,
                brand, category,
                sortBy, sortDirection,pageable);
    }

    @Override
    public Page<Product> getAllProduct(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
