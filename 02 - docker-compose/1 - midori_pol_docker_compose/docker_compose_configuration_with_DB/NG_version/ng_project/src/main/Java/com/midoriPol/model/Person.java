package com.midoriPol.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Person {
    @Id
    @Column(nullable = false)
    private String id; // String
    @OneToOne(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    public Person() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Cart getCart() {
        return cart;
    }
}