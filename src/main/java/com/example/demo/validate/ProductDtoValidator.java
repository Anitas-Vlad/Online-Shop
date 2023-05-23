package com.example.demo.validate;

import com.example.demo.dto.ProductDto;
import com.example.demo.model.enums.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;


@Component
public class ProductDtoValidator {

    public void validate(ProductDto productDto, MultipartFile multipartFile, BindingResult bindingResult) {
        validateName(productDto, bindingResult);
        validatePrice(productDto, bindingResult);
        validateCategory(productDto, bindingResult);
        validateQuantity(productDto, bindingResult);
        validateImage(multipartFile, bindingResult);
    }

    public void validateImage(MultipartFile multipartFile, BindingResult bindingResult) {
        try {
            byte[] image = multipartFile.getBytes();
            if (image.length > 0) {
                return;
            }
        } catch (Exception e) { //TODO  ?  MissingServletRequestPartExceptionMissingServletRequestPartException

        }
        FieldError fieldError = new FieldError("productDto", "image", "Invalid image.");
        bindingResult.addError(fieldError);
    }

    private void validatePrice(ProductDto productDto, BindingResult bindingResult) {
        try {
            Integer price = Integer.valueOf(productDto.getPrice());
            if (Integer.parseInt(productDto.getPrice()) < 0) {
                FieldError fieldError = new FieldError("productDto", "price", "Invalid price.");
                bindingResult.addError(fieldError);
            }
        } catch (NumberFormatException exception) {
            FieldError fieldError = new FieldError("productDto", "price", "Invalid price.");
            bindingResult.addError(fieldError);
        }
    }

    private void validateName(ProductDto productDto, BindingResult bindingResult) {
        if (productDto.getName().length() == 0) {
            FieldError fieldError = new FieldError("productDto", "name", "Please input a valid name.");
            bindingResult.addError(fieldError);
        }
    }

    private void validateCategory(ProductDto productDto, BindingResult bindingResult) {
        try {
            Category category = Category.valueOf(productDto.getCategory());
        } catch (IllegalArgumentException exception) {
            FieldError fieldError = new FieldError("productDto", "category", "Please insert a valid category");
            bindingResult.addError(fieldError);
        }
    }

    private void validateQuantity(ProductDto productDto, BindingResult bindingResult) {
        try {
            Integer price = Integer.valueOf(productDto.getPrice());
            if (Integer.parseInt(productDto.getPrice()) < 0) {
                FieldError fieldError = new FieldError("productDto", "quantity", "Please insert a positive number as a quantity.");
                bindingResult.addError(fieldError);
            }
        } catch (NumberFormatException exception) {
            FieldError fieldError = new FieldError("productDto", "quantity", "Please input a valid number for quantity.");
            bindingResult.addError(fieldError);
        }
    }
}
