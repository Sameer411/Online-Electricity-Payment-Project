package com.group2.capstone.EBPaymentSystem.billing.controller;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.authentication.services.UserService;
import com.group2.capstone.EBPaymentSystem.billing.models.Bill;
import com.group2.capstone.EBPaymentSystem.billing.models.Property;
import com.group2.capstone.EBPaymentSystem.billing.service.BillingService;
import com.group2.capstone.EBPaymentSystem.notification.producer.NotificationProducer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

@Controller
@Transactional
public class BillingCronJobController {

    @Autowired
    private UserService userService;

    @Autowired
    private BillingService billService;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private NotificationProducer notificationProducer;

    @Scheduled(cron = "00 59 23 28-30 * *")
    public void getAllUsers() {
        List<User> users = userService.getAllConsumers();
        for (User user : users) {
            generateBillEveryMonth(user);
        }
    }


    public void generateBillEveryMonth(User user) {

        System.out.println(user);
        List<Property> properties = billService.getUserProperties(user);
        System.out.println("properties fetched");

        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();
        for (Property property : properties) {
            Bill bill = billService.calculateBill(property, month, year);
            String subjectString = "Bill Generated for the month " + Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.UK) + " " + year;
            String messageString = "Your bill for the month of " + Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.UK) + " " + year + " is generated and the total amount outstanding is " + bill.getAmount();
            notificationProducer.sendNotification(user.getUserProfile(), subjectString, messageString);
            System.out.println("bill calculated");

        }

    }

}
