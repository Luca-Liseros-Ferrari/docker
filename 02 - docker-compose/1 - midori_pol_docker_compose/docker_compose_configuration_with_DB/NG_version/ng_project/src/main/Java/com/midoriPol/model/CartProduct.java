package com.midoriPol.model;

import javax.persistence.*;


import java.util.ArrayList;
import java.util.List;

@NamedQueries({
        @NamedQuery(name = "quantityPlusOne", query = "select cp " +
                "from com.midoriPol.model.CartProduct cp " +
                "where cp.product.id = :productId" +
                " and cp.cart.id = :cartId "
        ),

        @NamedQuery(name = "quantityMinusOne", query = "select cp " +
                "from com.midoriPol.model.CartProduct cp " +
                "where cp.product.id = :productId" +
                " and cp.cart.id = :cartId "
        ),

        @NamedQuery(name = "avoidPlusIfExclusive", query = "select cp " +
                "from com.midoriPol.model.CartProduct cp " +
                "where cp.category = :exclusive" +
                " and cp.cart.id = :cartId "
        ),
})
@Entity
public class CartProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private CategoriaProdotto category;
    private Long price;
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;
    public void setPerson(Person person) {
        this.person = person;
    }
    public Person getPerson() {
        return person;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public CategoriaProdotto getCategory() {
        return category;
    }
    public void setCategory(CategoriaProdotto category) {
        this.category = category;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    // Metodo per associare il prodotto, il carrello e la persona al CartProduct
    public void associo (Long id, Cart cart, Person person) {
        this.id = id;
        this.cart = cart;
        this.person = person;
    }
    public Long getPrice() {
      return price;
    }

  public void setPrice(Long price) {
      this.price = price;
  }
}
