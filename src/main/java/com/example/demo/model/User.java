package com.example.demo.model;

import com.example.demo.model.enums.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.catalina.LifecycleState;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Wishlist wishlist;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy = "user") // numele variabilei din order care face referire la user
    private List<Order> orders;
}
