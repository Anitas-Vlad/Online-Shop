package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Stock {

    @Id
    @GeneratedValue
    private Integer stockId;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private Product product;
    private Integer quantity;


    public void decreaseQuantityByOne(){
        quantity -= 1;
    }
}
