package com.midoriPol.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    private List<CartProduct> CartProducts = new ArrayList<>();

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<CartProduct> getProducts() {
        return CartProducts;
    }

    public void setCartProducts(List<CartProduct> products) {
        this.CartProducts = products;
    }

    public void addProduct(CartProduct product) {
        CartProducts.add(product);
        product.setCart(this);
        // CartProduct.setPerson(person);
    }
    public void removeProduct(CartProduct product) {
        CartProducts.remove(product);
    }

    public List<CartProduct> getCartProducts() {
        return CartProducts;
    }
}