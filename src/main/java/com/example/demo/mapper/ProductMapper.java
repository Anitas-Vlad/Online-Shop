package com.example.demo.mapper;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.Product;
import com.example.demo.model.Stock;
import com.example.demo.model.enums.Category;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ProductMapper {

    public Product map(ProductDto productDto, MultipartFile multipartFile){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(Integer.parseInt(productDto.getPrice()));
        product.setCategory(Category.valueOf(productDto.getCategory()));
        product.setDescription(productDto.getDescription());
        product.setImage(getBytes(multipartFile));

        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(Integer.valueOf(productDto.getQuantity()));
        product.setStock(stock);
        return product;
    }

    private byte[] getBytes(MultipartFile multipartFile) {
        try {
            return multipartFile.getBytes();
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public ProductDto map(Product product){
        ProductDto productDto = new ProductDto();
        productDto.setProductId(product.getProductId().toString());
        productDto.setName(product.getName());
        productDto.setPrice(String.valueOf(product.getPrice()));
        productDto.setCategory(String.valueOf(product.getCategory()));
        productDto.setQuantity(product.getQuantityInStock().toString());
        productDto.setDescription(product.getDescription());
        productDto.setImage(Base64.encodeBase64String(product.getImage())); // Base 64 encoding !!
        return productDto;
    }
}
