package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.entity.Order;
import com.aaronbujatin.behera.repository.OrderRepository;
import com.aaronbujatin.behera.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(value = "http://localhost:4200")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order){
        Order orderResponse = orderService.save(order);
        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrderByUserId(){
        List<Order> response = orderService.getAllOrder();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
