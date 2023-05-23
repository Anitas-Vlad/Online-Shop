package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class ShoppingCart {
    @Id
    @GeneratedValue
    private Integer shoppingCartId;

    @OneToOne
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "shoppingCartId"), inverseJoinColumns = @JoinColumn(name = "productId"))
    private List<Product> products;

    public void addProduct(Product product){
        products.add(product);
    }
}

