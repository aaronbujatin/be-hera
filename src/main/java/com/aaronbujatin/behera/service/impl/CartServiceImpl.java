package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.entity.User;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.exception.ResourceNotFoundException;
import com.aaronbujatin.behera.repository.CartItemRepository;
import com.aaronbujatin.behera.repository.CartRepository;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.CartService;
import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addItemToCart(CartItem cartItem) {
        //Get the authenticated user and get its cart
        User user = userService.getUser();
        Cart cart = user.getCart();

        // Check if the user has a cart; if not, create a new cart and save
        if (cart == null) {
            cart = new Cart();
            user.setCart(cart);
            cart.setUser(user);
            cartRepository.save(cart);
        }
        // Check if the product is already in the cart
        Optional<CartItem> productInCart = cart.getCartItems()
                .stream()
                .filter(ci -> ci.getProduct().getId().equals(cartItem.getProduct().getId()))
                .findFirst();

        //check if the product was present
        if (productInCart.isPresent()) {
            // Product is already in the cart and check if the quantity is available in stock
            if (productInCart.get().getProduct().getStock() < productInCart.get().getQuantity() + cartItem.getQuantity()) {
                throw new InvalidArgumentException("Product does not have the desired stock");
            }
        } else {
            // Product is not in the cart, check stock before adding
            if (cartItem.getQuantity() > cartItem.getProduct().getStock()) {
                throw new InvalidArgumentException("Product does not have the desired stock");
            }

            // If the product was not present in CartItem then Create and add a new CartItem Object
            CartItem cartItem1 = new CartItem();
            cartItem1.setQuantity(cartItem.getQuantity());
            cartItem1.setProduct(cartItem.getProduct());
            cartItem1.setCart(cart);
            cart.getCartItems().add(cartItem1);
        }

        // Save the cart and calculate total amount
        cart = calculateTotalAmount(cart);
        cart.setUser(user);
        cart.setDateCreated(LocalDate.now());
        cartRepository.save(cart);

        return cartItem;
    }


//    @Override
//    public CartItem addItemToCart(CartItem cartItem) {
//        User user = userService.getUser();
//        Cart cart = user.getCart();
//        if (Objects.nonNull(cart) && Objects.nonNull(cart.getCartItems()) && !cart.getCartItems().isEmpty()) {
//            Optional<CartItem> productInCart = cart.getCartItems()
//                    .stream()
//                    .filter(ci -> ci.getProduct().getId().equals(cartItem.getProduct().getId()))
//                    .findFirst();
//            if (productInCart.isEmpty()) {
//                if (productInCart.get().getProduct().getStock() < productInCart.get().getQuantity() + cartItem.getQuantity()) {
//                    throw new InvalidArgumentException("Product does not have a desired stock");
//                }
//                Cart updatedCart = calculateTotalAmount(cart);
//                cartRepository.save(updatedCart);
//            }
//        }
//        Product product = productRepository.findById(cartItem.getProduct().getId())
//                .orElseThrow(() -> new ResourceNotFoundException("Product with id of " + cartItem.getProduct().getId()  + " was not found!"));
//
//        CartItem cartItem1 = new CartItem();
//        cartItem1.setQuantity(cartItem.getQuantity());
//        cartItem1.setProduct(product);
//        cartItemRepository.save(cartItem1);
////        cart.getCartItems().add(cartItem1);
//        calculateTotalAmount(cart);
//        cartRepository.save(cart);
//        return cartItem1;
//    }

    @Override
    public CartItem incrementItemToCart(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem decrementItemToCart(CartItem cartItem) {
        return null;
    }

    @Override
    public CartItem removeItemFromCart(Long id) {
        return null;
    }

    @Override
    public void emptyItemFromCart() {

    }

    @Override
    public Cart calculateTotalAmount(Cart cart) {
        cart.setTotalAmount(0);

        cart.getCartItems().forEach(cartItem -> {
                    cart.setTotalAmount(cartItem.getProduct().getPrice() * cartItem.getQuantity());
                }
        );
        cart.setTotalAmount(cart.getTotalAmount());
        return cart;
    }


}
