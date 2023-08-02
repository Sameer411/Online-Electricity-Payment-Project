package com.group2.capstone.EBPaymentSystem.notification.consumer;

import com.group2.capstone.EBPaymentSystem.billing.models.UserProfile;
import com.group2.capstone.EBPaymentSystem.notification.config.RabbitMQConfig;
import com.group2.capstone.EBPaymentSystem.notification.models.NotificationMessage;
import com.group2.capstone.EBPaymentSystem.notification.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveNotification(NotificationMessage notificationMessage) {
//        System.out.println("Notification Consumer:" + notificationMessage);
        UserProfile userProfile = notificationMessage.getUserProfile();
        String subject = notificationMessage.getSubject();
        String message = notificationMessage.getMessage();
        notificationService.sendNotification(userProfile, subject, message);
    }
}