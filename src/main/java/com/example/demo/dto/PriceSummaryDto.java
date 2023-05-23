package com.example.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceSummaryDto {
    private Integer subtotal;
    private Integer shipping;
    private Integer total;
}
