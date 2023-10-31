package com.aaronbujatin.behera.service.impl;

import com.aaronbujatin.behera.entity.*;
import com.aaronbujatin.behera.exception.InvalidArgumentException;
import com.aaronbujatin.behera.repository.*;
import com.aaronbujatin.behera.service.OrderService;
import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;


    @Override
    public Order save() {
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
        Cart cartToDelete = cartRepository.findByUserId(user.getId());
        if(cartToDelete != null){
            cartRepository.delete(cartToDelete);
        } else {
            throw new InvalidArgumentException("Cart to delete was empty or null.");
        }

        return order;
    }

    @Override
    public List<Order> getAllOrder() {
        return null;
    }

    @Override
    public Order calculateTotalAmount(Order order) {
        order.setTotalAmount(0);
        order.getOrderItems()
                .forEach(orderItem -> {
                            order.setTotalAmount(order.getTotalAmount() + (orderItem.getProduct().getPrice() * orderItem.getQuantity()));
                        }
                );
        return order;
    }
}