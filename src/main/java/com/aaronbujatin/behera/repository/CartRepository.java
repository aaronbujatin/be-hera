
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.Cart;
import com.aaronbujatin.behera.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserId(Long id);
    List<Cart> findByUser_Id(Long userId);
    void deleteByUserId(Long id);



}

