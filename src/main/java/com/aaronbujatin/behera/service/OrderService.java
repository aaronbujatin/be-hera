package com.aaronbujatin.behera.service;

import com.aaronbujatin.behera.entity.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    List<Order> getAllOrder();

    Order calculateTotalAmount(Order order);


}
