
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long id);

}

