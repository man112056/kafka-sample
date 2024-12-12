package com.example.inventory.inventory_service.component;


import com.example.inventory.inventory_service.model.PaymentProcessedEvent;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class InventoryConsumer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final Gson gson;

    // Constructor injection for KafkaTemplate
    @Autowired
    public InventoryConsumer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.gson = new Gson();
    }

    @KafkaListener(topics = "payment-processed", groupId = "inventory-service")
    public void consumePayment(String message) {
        // Deserialize the payment event from the incoming message
        PaymentProcessedEvent paymentEvent = gson.fromJson(message, PaymentProcessedEvent.class);

        if (paymentEvent.isPaymentSuccess()) {
            System.out.println("Updating stock for order: " + paymentEvent.getOrderId());

            // Simulate stock update by publishing an event
            kafkaTemplate.send("stock-updated", paymentEvent.getOrderId());
        }
    }
}