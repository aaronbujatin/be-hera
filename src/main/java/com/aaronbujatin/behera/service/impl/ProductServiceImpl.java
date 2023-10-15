package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
