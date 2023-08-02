package com.group2.capstone.EBPaymentSystem.billing.controller;

import com.group2.capstone.EBPaymentSystem.authentication.models.User;
import com.group2.capstone.EBPaymentSystem.authentication.services.UserService;
import com.group2.capstone.EBPaymentSystem.billing.models.Bill;
import com.group2.capstone.EBPaymentSystem.billing.models.Property;
import com.group2.capstone.EBPaymentSystem.billing.service.BillingService;
import com.group2.capstone.EBPaymentSystem.notification.producer.NotificationProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/bill/generator")
public class BillingController {

    @Autowired
    private UserService userService;

    @Autowired
    private BillingService billService;
    @Autowired
    private NotificationProducer notificationProducer;

    @GetMapping("/{userid}")
    public String generateBill(@PathVariable long userid, @RequestParam int month, @RequestParam int year) {
        Optional<User> user = userService.findById(userid);
        System.out.println("user fetched");
        System.out.println(user);
        List<Property> properties = billService.getUserProperties(user.get());
        System.out.println("properties fetched");
        List<Bill> bills = new ArrayList<>();
        for (Property property : properties) {
            Bill bill = billService.calculateBill(property, month, year);
            String subjectString = "Bill Generated for the month " + Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.UK) + " " + year;
            String messageString = "Your bill for the month of " + Month.of(month).getDisplayName(TextStyle.FULL_STANDALONE, Locale.UK) + " " + year + " is generated and the total amount outstanding is " + bill.getAmount();
            notificationProducer.sendNotification(user.get().getUserProfile(), subjectString, messageString);
            bills.add(bill);
        }
        return bills.toString();
    }


    @GetMapping("/pdf/{userid}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable long userid) throws IOException {
        Optional<User> user = userService.findById(userid);
        List<Bill> latestBills = billService.getBillForUser(user.get());
        try {
            byte[] pdfBytes = billService.pdfGenerator(user.get(), latestBills);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "bill.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/pdfbydate/{userid}")
    public ResponseEntity<byte[]> generatePDFforPrevBill(@PathVariable long userid, @RequestParam int month, @RequestParam int year) {
        Optional<User> user = userService.findById(userid);
        List<Bill> bills = billService.getBillForPrevMonths(user.get(), month, year);
        try {
            byte[] pdfBytes = billService.pdfGenerator(user.get(), bills);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "bill.pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            // Handle exceptions and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }


}