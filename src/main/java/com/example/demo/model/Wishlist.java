package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Wishlist {
    @Id
    @GeneratedValue
    private Integer wishlistId;

    @OneToOne
    private User user;

    @ManyToMany(mappedBy = "wishlists") // numele variabilei care face referire la product
    private List<Product> products;
}
