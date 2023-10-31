package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.Order;
import com.aaronbujatin.behera.repository.OrderRepository;
import com.aaronbujatin.behera.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<Order> save(){
        Order order = orderService.save();
        return new ResponseEntity<>(order, HttpStatus.OK);
    }


}
