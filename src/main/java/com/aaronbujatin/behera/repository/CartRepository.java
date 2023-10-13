package com.aaronbujatin.behera.repository;

import com.aaronbujatin.practicethree.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
