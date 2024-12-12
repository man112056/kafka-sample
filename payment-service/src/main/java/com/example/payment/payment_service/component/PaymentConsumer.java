package com.example.payment.payment_service.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.payment.payment_service.model.Order;
import com.example.payment.payment_service.model.PaymentProcessedEvent;
import com.google.gson.Gson;


@Component
public class PaymentConsumer {

        private final KafkaTemplate<String, String> kafkaTemplate;
        private final Gson gson;


         // Constructor injection for KafkaTemplate
    @Autowired
    public PaymentConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = new Gson();
    }

    @KafkaListener(topics = "order-created", groupId = "payment-service")
    public void consumeOrder(String message) {
        Order order = new Gson().fromJson(message, Order.class);
        System.out.println("Processing payment for order: " + order.getOrderId());

        // Simulate payment processing
        String paymentEvent = new Gson().toJson(
            new PaymentProcessedEvent(order.getOrderId(), true)
        );
        kafkaTemplate.send("payment-processed", paymentEvent);
    }
}

