package com.group2.capstone.EBPaymentSystem.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SMSNotificationService {
    //    @Autowired
//    SMSSender smsSender;
    void sendSMSNotification(String phoneNumber, String message) {
//        smsSender.sendSMS(phoneNumber, message);
        System.out.println("Inside SMS Notification:"+"Phone Number:" + phoneNumber + "," + "Message:" + message);
    }

}
