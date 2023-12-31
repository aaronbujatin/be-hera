
package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.repository.CartItemRepository;
import com.aaronbujatin.behera.repository.CartRepository;
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
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @PostMapping()
    public ResponseEntity<CartItem> addItemToCart(@RequestBody CartItem cartItem) {
        CartItem cartItemResponse = cartService.addItemToCart(cartItem);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @PostMapping("/increment")
    public ResponseEntity<CartItem> incrementCartItemQuantity(@RequestBody CartItem cartItem){
        CartItem cartItemResponse = cartService.incrementItemToCart(cartItem);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @PostMapping("/decrement")
    public ResponseEntity<CartItem> decrementCartItemQuantity(@RequestBody CartItem cartItem){
        CartItem cartItemResponse = cartService.decrementItemToCart(cartItem);
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable Long id) {
        return new ResponseEntity<>(cartService.deleteCartItemById(id), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Cart>> getAllItemInCart(){
        List<Cart> cartItemResponse = cartService.getAllCart();
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }


    @DeleteMapping("delete-cart")
    public ResponseEntity<String> deleteCart(){
        cartRepository.deleteAll();
        return new ResponseEntity<>("Cart deleted", HttpStatus.OK);
    }
}

