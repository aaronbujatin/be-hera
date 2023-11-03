
package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.entity.Product;
import com.aaronbujatin.behera.entity.User;
import com.aaronbujatin.behera.exception.CannotDeleteResourceException;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.exception.ResourceNotFoundException;
import com.aaronbujatin.behera.repository.CartItemRepository;
import com.aaronbujatin.behera.repository.CartRepository;
import com.aaronbujatin.behera.repository.ProductRepository;
import com.aaronbujatin.behera.service.CartService;
import com.aaronbujatin.behera.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addItemToCart(CartItem cartItemRequest) {
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
                .filter(ci -> ci.getProduct().getId().equals(cartItemRequest.getProduct().getId()))
                .findFirst();

        //check if the product was present
        if (productInCart.isPresent()) {
            // Product is already in the cart and check if the quantity is available in stock
            int productQuantity = productInCart.get().getQuantity() + cartItemRequest.getQuantity();
            int productStock = productInCart.get().getProduct().getStock();

            if (productStock < productQuantity) {
                throw new InvalidArgumentException("Product does not have the desired stock");
            }

            Product product = cartItemRequest.getProduct();

            productInCart.get().setQuantity(productQuantity);
            productInCart.get().setProduct(product);
            productInCart.get().setCart(cart);

            Product updatedProduct = productRepository.findById(product.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product " + product.getId() + " was not found!"));

            int currentStock = updatedProduct.getStock() - productQuantity;
            updatedProduct.setStock(currentStock);
            productRepository.save(updatedProduct);

        } else {
            // Product is not in the cart, check stock before adding

            Product product = cartItemRequest.getProduct();

            Product productInDatabase = productRepository.findById(product.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product " + product.getId() + " was not found!"));

            int productQuantity = cartItemRequest.getQuantity();
            int productStock = productInDatabase.getStock();

            if (productStock < productQuantity) {
                throw new InvalidArgumentException("Product does not have the desired stock");
            }

//          If the product was not present in CartItem then Create and add a new CartItem Object
            CartItem cartItem = new CartItem();
            cartItem.setQuantity(productQuantity);
            cartItem.setProduct(product);
            cartItem.setCart(cart);
            cart.getCartItems().add(cartItem);

            int currentStock = productStock - productQuantity;
            product.setStock(currentStock);
            productRepository.save(product);
        }

        // Save the cart and calculate total amount
        cart = calculateTotalAmount(cart);
        cart.setUser(user);
        cart.setDateCreated(LocalDate.now());
        cartRepository.save(cart);

        return cartItemRequest;
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
    @Transactional
    public String deleteById(Long id) {
        User user = userService.getUser();
        Cart cart = user.getCart();
        user.setCart(null);

        return "cart item deleted";

//        try {
//            User user = userService.getUser();
//            Cart cart = user.getCart();
//            List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getId());
//
//            if (!cartItems.isEmpty()) {
//                log.info("Cart Items deletion: {}", cartItems);
//                cartItems.forEach(cartItem -> cartItemRepository.deleteByCartId(cart.getId()));
//                cartRepository.deleteByUserId(user.getId());
//                log.info("Cart deletion");
//                return "Cart successfully deleted!";
//            } else {
//                throw new ResourceNotFoundException("Cart not found for user id: ");
//            }
//        } catch (Exception e) {
//            log.error("Error deleting cart with id: " , e);
//            throw new CannotDeleteResourceException("Error deleting cart.");
//        }
    }

    @Override
    public void emptyItemFromCart() {

    }

    @Override
    public Cart calculateTotalAmount(Cart cart) {
        cart.setTotalAmount(0);
        cart.getCartItems()
                .forEach(cartItem -> {
                            cart.setTotalAmount(cart.getTotalAmount() + (cartItem.getProduct().getPrice() * cartItem.getQuantity()));
                        }
                );
        return cart;
    }

    @Override
    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    @Override
    public Cart deleteByUser(Long id) {
        return null;
    }

    @Override
    public List<CartItem> getAllCartItem() {
        return cartItemRepository.findAll();
    }

    @Override
    @Transactional
    public String deleteByCartId(Long id) {
//        cartItemRepository.deleteByCartId(id);
        Cart cart = cartRepository.findById(id).get();
        if (cart != null) {
            cartRepository.deleteById(cart.getId());
            return "Cart item deleted";
        } else {
            throw new CannotDeleteResourceException("Cannot delete cart");
        }
    }


}
