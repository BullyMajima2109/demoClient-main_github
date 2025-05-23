package com.example.democlient.service;

import java.util.Optional;

import com.example.democlient.model.Cart;

public interface CartService {
    Cart createCart(Cart cart);
    Optional<Cart> getCartById(Long id);
    Cart updateCart(Cart cart);
    void deleteCart(Long id);

}
