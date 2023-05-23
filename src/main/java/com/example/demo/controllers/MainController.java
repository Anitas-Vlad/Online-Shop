package com.example.demo.controllers;

import com.example.demo.dto.*;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ShoppingCartService;
import com.example.demo.service.UserService;
import com.example.demo.validate.ProductDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDtoValidator productDtoValidator;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private OrderService orderService;


    @GetMapping("/addProduct")
    public String addProductGet(Model model, @ModelAttribute("addProductMessage") String addProductMessage) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute(name = "productDto") ProductDto productDto, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes, @RequestParam(value = "productImage", required = false) MultipartFile multipartFile) {
        productDtoValidator.validate(productDto, multipartFile, bindingResult);
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }
        productService.addProduct(productDto, multipartFile);
        redirectAttributes.addFlashAttribute("addProductMessage", "The product was successfully added");
        return "redirect:/addProduct";
    }

    @GetMapping("/home")
    public String homeGet(Model model) {
        List<ProductDto> productDtoList = productService.getAllAvailableProductDtos();
        model.addAttribute("productDtoList", productDtoList);
        return "home";
    }

    @GetMapping("/product/{productId}")
    public String viewProductGet(@PathVariable(value = "productId") String productId, Model model) {
        Optional<ProductDto> optionalProductDto = productService.findProductDtoById(productId);
        if (optionalProductDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("productDto", optionalProductDto.get());
        model.addAttribute("quantityDto", new QuantityDto());
        return "viewProduct";
    }

    @PostMapping(value = "/product/{productId}")
    public String viewProductDelete(@PathVariable(value = "productId") String productId) {
        productService.deleteProduct(productId);
        return "redirect:/home";
    }

    @GetMapping(value = "/login")
    public String loginGet() {
        return "login";
    }

    @GetMapping(value = "/registration")
    public String registrationGet(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registrationPost(@ModelAttribute(name = "userDto") UserDto userDto) {
        userService.registerUser(userDto);
        return "redirect:/login";
    }

    @PostMapping(value = "/product/{productId}/add")
    public String addToCardPost(@PathVariable(value = "productId") String productId,
                                @ModelAttribute(name = "quantityDto")QuantityDto quantityDto,
                                Authentication authentication){
        System.out.println(productId + "    " + quantityDto.getQuantity()+ authentication.getName());
        shoppingCartService.addProduct(productId, quantityDto, authentication.getName());
        return "redirect:/product/" + productId;
    }

    @GetMapping(value = "/buyNow")
    public String buyNowGet(Authentication authentication, Model model){
        Map<ProductDto, QuantityPriceDto> productMap = shoppingCartService.retrieveShoppingCartContent(authentication.getName());
        model.addAttribute("productMap", productMap);

        PriceSummaryDto priceSummaryDto =shoppingCartService.computePriceSummaryDto(productMap);
        model.addAttribute("priceSummaryDto" , priceSummaryDto);

        return "buyNow";
    }

    @GetMapping(value ="/confirmation")
    public String confirmationGet(Authentication authentication){
        orderService.placeOrder(authentication.getName());
        return "confirmation";
    }
    @GetMapping(value = "/orders")
    public String ordersGet(Authentication authentication , Model model){
        List<OrderDto> orders = orderService.createOrderDtoList(authentication.getName());
        model.addAttribute("orders" , orders);
        return "orders";
    }
}
