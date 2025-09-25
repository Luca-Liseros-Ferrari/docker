package com.midoriPol.model;

import javax.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer quantity;
    private String description;
    private Long price;
    private CategoriaProdotto category;
    private String img;

   // private Long userId;

    public Product() {
    }

    public Product
            (String name, Integer quantity, Long price,
             CategoriaProdotto category, String description, String img) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.category = category;
        this.description = description;
        this.img = img;
    }
    // public Long getUserId() {return userId;}

   // public void setUserId(Long userId) {this.userId = userId;}


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public CategoriaProdotto getCategory() {
        return category;
    }

    public void setCategory(CategoriaProdotto category) {
        this.category = category;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

}
