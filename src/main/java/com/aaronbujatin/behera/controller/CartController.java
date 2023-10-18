
package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem) {
        CartItem cartItemResponse = cartService.addItemToCart(cartItem);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<CartItem>> getAllItemInCart(){
        List<CartItem> cartItemResponse = cartService.getAllCartItem();
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

//    @GetMapping()
//    public ResponseEntity<List<Cart>> getCartItemById(){
//        List<Cart> cartResponse = cartService.getAllCart();
//        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
//    }

}

