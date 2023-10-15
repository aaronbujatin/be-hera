package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


}
