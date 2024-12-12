package com.example.notification.notification_service.component;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "stock-updated", groupId = "notification-service")
    public void consumeStockUpdate(String orderId) {
        System.out.println("Order fulfilled. Notification sent for order: " + orderId);
    }
}

