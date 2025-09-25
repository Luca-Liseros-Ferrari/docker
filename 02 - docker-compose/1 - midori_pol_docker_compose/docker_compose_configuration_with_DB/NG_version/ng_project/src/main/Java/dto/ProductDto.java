package dto;
import com.midoriPol.model.Cart;
import com.midoriPol.model.CategoriaProdotto;
import com.midoriPol.model.Person;
import javax.persistence.*;
public class ProductDto {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    private Long id;
    private Cart cart;
    private String name;
    private Integer quantity;
    private String description;
    private Long price;
    private CategoriaProdotto category;
    private String img;

    public ProductDto() {}
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
    public Person getPerson() {
        return person;
    }
    private Person person;
    public Cart getCart() {
        return cart;
    }
    public void setCart(Cart cart) {
        this.cart = cart;
    }
    public void setPerson(Person person) {
        this.person = person;
    }

}
