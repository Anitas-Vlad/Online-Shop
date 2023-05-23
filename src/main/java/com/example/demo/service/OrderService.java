package com.example.demo.service;

import com.example.demo.dto.OrderDto;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.model.Order;
import com.example.demo.model.Product;
import com.example.demo.model.ShoppingCart;
import com.example.demo.model.enums.OrderStatus;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;

    public void placeOrder(String userEmail){
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(userEmail);
        Order order = createOrder(shoppingCart);
        shoppingCart.setProducts(new ArrayList<>());
        for(Product product : order.getProducts()){
            product.decreaseQuantityByOne();
        }
        orderRepository.save(order);
    }

    private Order createOrder(ShoppingCart shoppingCart) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PLACED);
        order.setProducts(shoppingCart.getProducts());
        order.setUser(shoppingCart.getUser());
        order.setDateTime(LocalDateTime.now());
        return order;
    }

    public List<OrderDto> createOrderDtoList(String userEmail) {
        List<Order> orders = orderRepository.findByUserEmail(userEmail);
        List<OrderDto> result = new ArrayList<>();
        for(Order order : orders){
            result.add(orderMapper.map(order));
        }
        return result;
    }
}
