
package com.aaronbujatin.behera.repository;


import com.aaronbujatin.behera.entity.CartItem;
import com.aaronbujatin.behera.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long id);

    void deleteByCartId(Long id);


}
