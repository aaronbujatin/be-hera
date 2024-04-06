package com.aaronbujatin.behera.mapper;

import com.aaronbujatin.behera.dto.ProductDto;
import com.aaronbujatin.behera.entity.Product;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ProductDtoMapper implements Function<Product, ProductDto> {
    @Override
    public ProductDto apply(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                product.getDescriptions(),
                product.getDateCreated(),
                product.getStock(),
                product.getBrand(),
                product.getCategory()
        );
    }
}
