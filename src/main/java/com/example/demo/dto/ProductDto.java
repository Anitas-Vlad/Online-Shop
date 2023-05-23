package com.example.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "image")
@EqualsAndHashCode
public class ProductDto {
    private String productId;
    private String name;
    private String price;
    private String description;
    private String category;
    private String quantity;
    private String image;
}
