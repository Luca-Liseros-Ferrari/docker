package com.midoriPol.business;

import com.midoriPol.model.Cart;
import com.midoriPol.model.CartProduct;
import dto.CartDto;
import dto.CartProductDto;

import java.util.ArrayList;
import java.util.List;

public class CarrelloManager {
    // metodo per rendere visibile il carrello all'utente
    public static CartDto mapCartToDTO(Cart cart) {
        CartDto cartDTO = new CartDto();
        cartDTO.setId(cart.getId());
        List<CartProductDto> cartProductDTOs = new ArrayList<>();
        for (CartProduct cartProduct : cart.getCartProducts()) {
            CartProductDto cartProductDTO = new CartProductDto();
            cartProductDTO.setId(cartProduct.getId());
            cartProductDTO.setName(cartProduct.getName());
            cartProductDTO.setCategory(cartProduct.getCategory());
            cartProductDTO.setQuantity(cartProduct.getQuantity());
            cartProductDTO.setPrice(cartProduct.getPrice());
            cartProductDTOs.add(cartProductDTO);
        }
        cartDTO.setCartProducts(cartProductDTOs);
        return cartDTO;
    }
}
