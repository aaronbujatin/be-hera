package com.aaronbujatin.behera.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private double price;
    private List<String> imageUrl;
    private List<String> descriptions;
    private LocalDate dateCreated;
    private int stock;
    private String brand;
    private String category;


}
