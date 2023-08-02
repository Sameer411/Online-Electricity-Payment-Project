package com.group2.capstone.EBPaymentSystem.notification.service;

import com.group2.capstone.EBPaymentSystem.billing.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import com.example.yourpackage.sms.SMSSender;

@Service
public class NotificationService {
    @Autowired
    private SMSNotificationService smsNotificationService;
    @Autowired
    private EmailNotificationService emailNotificationService;

    public void sendNotification(UserProfile userProfile, String subject, String message) {
        // Send email notification
        emailNotificationService.sendEmailNotification(userProfile.getEmail(), subject, message);

        // Send SMS notification
        smsNotificationService.sendSMSNotification(userProfile.getContactNo(), message);
    }


}
