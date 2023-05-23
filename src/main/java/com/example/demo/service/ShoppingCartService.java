package com.example.demo.service;

import com.example.demo.dto.PriceSummaryDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.QuantityDto;
import com.example.demo.dto.QuantityPriceDto;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public boolean addProduct(String productId, QuantityDto quantityDto, String userEmail) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(userEmail);
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        if (optionalProduct.isEmpty()) {
        }
        Product product = optionalProduct.get();
        Integer quantity = Integer.valueOf(quantityDto.getQuantity());
        if (product.getQuantityInStock() < quantity) {
            System.out.println("product is out of stock");
            return false;
        }
        for (int index = 0; index < quantity; index++) {
            shoppingCart.addProduct(product);
        }
        shoppingCartRepository.save(shoppingCart);
        return true;
    }


    public Map<ProductDto, QuantityPriceDto> retrieveShoppingCartContent(String userEmail) {
        Map<ProductDto, QuantityPriceDto> result = new LinkedHashMap<>();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(userEmail);
        List<Product> products = shoppingCart.getProducts();
        products.forEach(product -> {
            ProductDto productDto = productMapper.map(product);
            if (result.containsKey(productDto)) {
                QuantityPriceDto quantityPriceDto  = result.get(productDto);
                Integer numberOfProducts = quantityPriceDto.getQuantity();
                quantityPriceDto.setQuantity(numberOfProducts+ 1);
                Integer totalPrice = quantityPriceDto.getTotalPrice();
                quantityPriceDto.setTotalPrice(totalPrice + Integer.parseInt(productDto.getPrice()));
            } else {
                QuantityPriceDto quantityPriceDto = new QuantityPriceDto();
                quantityPriceDto.setTotalPrice(Integer.valueOf(productDto.getPrice()));
                quantityPriceDto.setQuantity(1);
                result.put(productDto, quantityPriceDto);
            }
        });
        System.out.println(result);
        return result;
    }

    public PriceSummaryDto computePriceSummaryDto(Map<ProductDto, QuantityPriceDto> productMap) {
      int subTotalPrice =  productMap.values().stream()
                .map(QuantityPriceDto::getTotalPrice)
                .mapToInt(Integer::intValue)
                .sum();
      int shipping = 50;
      int total = subTotalPrice + shipping;
      PriceSummaryDto priceSummaryDto = new PriceSummaryDto(subTotalPrice , shipping , total);
      return  priceSummaryDto;
    }
}
