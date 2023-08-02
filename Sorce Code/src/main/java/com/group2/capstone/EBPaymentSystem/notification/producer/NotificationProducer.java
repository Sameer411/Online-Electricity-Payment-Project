package com.group2.capstone.EBPaymentSystem.notification.producer;

import com.group2.capstone.EBPaymentSystem.billing.models.UserProfile;
import com.group2.capstone.EBPaymentSystem.notification.config.RabbitMQConfig;
import com.group2.capstone.EBPaymentSystem.notification.models.NotificationMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendNotification(UserProfile userProfile, String subject, String message) {
        NotificationMessage notificationMessage = new NotificationMessage(userProfile, subject, message);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE,RabbitMQConfig.ROUTING_KEY, notificationMessage);
    }
}
