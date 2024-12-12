package com.example.order.order_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.order_service.model.Order;
import com.google.gson.Gson;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody Order order) {
        // Serialize and send order-created event
        kafkaTemplate.send("order-created", new Gson().toJson(order));
        return ResponseEntity.ok("Order Created!");
    }
}