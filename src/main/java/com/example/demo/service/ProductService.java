package com.example.demo.service;

import com.example.demo.dto.ProductDto;
import com.example.demo.mapper.ProductMapper;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    public void addProduct(ProductDto productDto, MultipartFile multipartFile) {
        Product product = productMapper.map(productDto, multipartFile);
        productRepository.save(product);
    }

    public List<ProductDto> getAllAvailableProductDtos() {
        List<Product> productList = productRepository.findAll();
        List<ProductDto> result = new ArrayList<>();
        productList.forEach(product -> result.add(productMapper.map(product)));
        return result;
    }

    public Optional<ProductDto> findProductDtoById(String productId) {
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        if (optionalProduct.isEmpty()) {
            return Optional.empty();
        }
        Product product = optionalProduct.get();
        ProductDto productDto = productMapper.map(product);
        return Optional.of(productDto);
    }

    public void deleteProduct(String productId) {
        try {
            Integer id = Integer.valueOf(productId);
            productRepository.deleteById(id);
        }catch (NumberFormatException exception){

        }
    }
}
