
package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.*;
import com.aaronbujatin.behera.exception.CannotDeleteResourceException;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.exception.ResourceNotFoundException;
import com.aaronbujatin.behera.repository.*;
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
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

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

//            Product updatedProduct = productRepository.findById(product.getId())
//                    .orElseThrow(() -> new ResourceNotFoundException("Product " + product.getId() + " was not found!"));
//
//            int currentStock = updatedProduct.getStock() - productQuantity;
//            updatedProduct.setStock(currentStock);
//            productRepository.save(updatedProduct);

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

//            int currentStock = productStock - productQuantity;
//            product.setStock(currentStock);
//            productRepository.save(product);
        }

        // Save the cart and calculate total amount
        cart = calculateTotalAmount(cart);
        cart.setUser(user);
        cart.setDateCreated(LocalDate.now());
        cartRepository.save(cart);

        return cartItemRequest;
    }


    @Override
    public CartItem incrementItemToCart(CartItem cartItemRequest) {

        User user = userService.getUser();
        Cart cart = user.getCart();
        Product product = cartItemRequest.getProduct();

        Product productInDatabase = productRepository.findById(product.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Product " + product.getId() + " was not found!"));

        //getting the quantity of product from cart item
        int productQuantity = cartItemRequest.getQuantity();
        //getting the stock of product from database
        int productStock = productInDatabase.getStock();

        //checking if the product has available stock
        if (productStock < productQuantity) {
            throw new InvalidArgumentException("You reached the maximum stock");
        } else {
            //find the product id in cart
            Optional<CartItem> productInCart = cart.getCartItems()
                    .stream()
                    .filter(ci -> ci.getProduct().getId().equals(cartItemRequest.getProduct().getId()))
                    .findFirst();

            //add the product quantity from cart item request to product quantity from the existing cartItems in cart that found
            int updatedQuantity = productQuantity + productInCart.get().getQuantity();

            //set the updated product quantity from cart item
            productInCart.get().setQuantity(updatedQuantity);

//            int currentStock = productInDatabase.getStock() - productQuantity;
//
//            productInDatabase.setStock(currentStock);
//            productRepository.save(productInDatabase);

            cart = calculateTotalAmount(cart);
            cartRepository.save(cart);

        }



//        int currentStock = productStock - productQuantity;
//        product.setStock(currentStock);
//        productRepository.save(product);


        return cartItemRequest;
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
        List<CartItem> cartItems = cart.getCartItems();

        if (cartItems.isEmpty()) {
            throw new InvalidArgumentException("Cannot place order. No cart items");
        }

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(cart.getTotalAmount());
        order.setDateCreated(cart.getDateCreated());
        log.info("Order object : {}", order);
        orderRepository.save(order);

        List<OrderItem> orderItems = cartItems.stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    return orderItem;
                })
                .collect(Collectors.toList());

        orderItemRepository.saveAll(orderItems);
        user.setCart(null);

        return "cart item deleted";

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
        User user = userService.getUser();
        return cartRepository.findByUser_Id(user.getId());
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
