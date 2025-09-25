package dto;

import com.midoriPol.model.CartProduct;

import java.util.List;

public class CartDto {
    private String userId;
    private Long id;
    private List<CartProductDto> cartProducts;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public List<CartProductDto> getCartProducts() {
        return cartProducts;
    }
    public void setCartProducts(List<CartProductDto> cartProducts) {
        this.cartProducts = cartProducts;
    }
    public CartDto() {}
    public String getUserId() {
      return userId;
    }
    public void setUserId(String userId) {
      this.userId = userId;
    }
}
