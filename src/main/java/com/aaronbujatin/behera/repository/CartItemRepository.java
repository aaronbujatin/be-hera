
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
