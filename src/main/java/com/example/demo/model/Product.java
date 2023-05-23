package com.example.demo.model;

import com.example.demo.model.enums.Category;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Product {
    @Id
    @GeneratedValue
    private Integer productId;

    private String name;
    private Integer price;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "productId"), inverseJoinColumns = @JoinColumn(name = "wishlistId"))
    private List<Wishlist> wishlists;

    @ManyToMany(mappedBy = "products", cascade = CascadeType.ALL)
    private List<ShoppingCart> shoppingCarts;

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private Stock stock;

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @Lob
    private byte[] image;

    public Integer getQuantityInStock(){
        return stock.getQuantity();
    }

    public void decreaseQuantityByOne(){
        stock.decreaseQuantityByOne();
    }

}
