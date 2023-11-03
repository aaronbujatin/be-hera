
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

    @GetMapping()
    public ResponseEntity<List<CartItem>> getAllItemInCart(){
        List<CartItem> cartItemResponse = cartService.getAllCartItem();
        return new ResponseEntity<>(cartItemResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable Long id){
        String response = cartService.deleteById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping()
//    public ResponseEntity<List<Cart>> getCartItemById(){
//        List<Cart> cartResponse = cartService.getAllCart();
//        return new ResponseEntity<>(cartResponse, HttpStatus.OK);
//    }
    @DeleteMapping("/cartItem/{id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable Long id){
        String response = cartService.deleteByCartId(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("delete-cart")
    public ResponseEntity<String> deleteCart(){
        cartRepository.deleteAll();
        return new ResponseEntity<>("Cart deleted", HttpStatus.OK);
    }
}

