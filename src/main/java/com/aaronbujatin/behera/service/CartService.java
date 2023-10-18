
package com.aaronbujatin.behera.service;


import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.CartItem;

import java.util.List;

public interface CartService {

    CartItem addItemToCart(CartItem cartItem);
    CartItem incrementItemToCart(CartItem cartItem);
    CartItem decrementItemToCart(CartItem cartItem);
    CartItem removeItemFromCart(Long id);
    void emptyItemFromCart();
    Cart calculateTotalAmount(Cart cart);
    List<Cart> getAllCart();

    List<CartItem> getAllCartItem();

}

