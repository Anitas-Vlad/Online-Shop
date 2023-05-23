package com.example.demo.mapper;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    private ProductMapper productMapper;

    public OrderDto map(Order order){
        OrderDto orderDto = new OrderDto();
        orderDto.setStatus(order.getOrderStatus().name());
        orderDto.setTime(order.getDateTime().toString());
        orderDto.setProductDtos(fromProductToProductDto(order.getProducts()));
        return orderDto;
    }

    private List<ProductDto> fromProductToProductDto(List<Product> products) {
        List<ProductDto> result = new ArrayList<>();
        for(Product product : products){
            result.add(productMapper.map(product));
        }
        return result;
    }
}











