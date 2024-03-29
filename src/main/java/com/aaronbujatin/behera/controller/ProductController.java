
package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
@CrossOrigin(origins = "http://localhost:4200")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.saveProduct(product), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Long id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<Product>> getAllProduct(@RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        return new ResponseEntity<>(productService.getAllProduct(pageable), HttpStatus.OK);
    }

    @GetMapping("/new-arrival")
    public ResponseEntity<List<Product>> getFiveNewArrivalProduct(){
        return new ResponseEntity<>(productService.getFiveRecentAddedProduct(), HttpStatus.OK);
    }

    @GetMapping("/collections")
    public ResponseEntity<List<Product>> getAllProductByBrand(@RequestParam String category,
                                                              @RequestParam(required = false) List<String> brands){
        List<Product> productsResponse;
        if(brands == null){
            productsResponse = productService.getProductsByCategory(category);
        } else {
            productsResponse = productService.getProductsByCategoryAndBrands(category, brands);
        }
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/brands/{category}")
    public ResponseEntity<Map<String, Long>> getBrandsByCategory(@PathVariable String category){
        Map<String, Long> brandsResponse = productService.getBrandsByCategory(category);
        return new ResponseEntity<>(brandsResponse, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Product>> getMotherboardsInPriceRange(
            @RequestParam("category") String category,
            @RequestParam("minPrice") double minPrice,
            @RequestParam("maxPrice") double maxPrice) {
        List<Product> productResponse = productService.getProductsByCategoryAndPriceBetween(category, minPrice, maxPrice);
        return new ResponseEntity<>(productResponse, HttpStatus.OK);
    }

    @GetMapping("/max-price/{category}")
    public ResponseEntity<Double> getMaxPriceByCategory(@PathVariable String category){
        Optional<Double> maxPriceResponse = productService.getMaxPriceByCategory(category);
        return maxPriceResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<Product>> getProductsByFilter(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String category,
            @RequestParam(required = false, defaultValue = "price") String sortBy,
            @RequestParam(required = false, defaultValue = "ASC") String sortDirection){

        Pageable pageable = PageRequest.of(page, size);

        Page<Product> filteredProducts = productService.getProductsByFilter(minPrice, maxPrice, brand,
                category, sortBy,
                sortDirection, pageable);

        return new ResponseEntity<>(filteredProducts, HttpStatus.OK);
    }



    //update
    //delete


}
