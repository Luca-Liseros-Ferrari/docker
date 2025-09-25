package dto;

import com.midoriPol.model.Cart;
import com.midoriPol.model.CategoriaProdotto;
import com.midoriPol.model.Person;
import com.midoriPol.model.Product;

import javax.persistence.*;

public class CartProductDto {
    private Long id;
    private String name;
    private Integer quantity;
    private Long price;
    private CategoriaProdotto category;
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

   public Long getPrice() {
    return price;
    }
    public void setPrice(Long price) {
    this.price = price;
    }
}
