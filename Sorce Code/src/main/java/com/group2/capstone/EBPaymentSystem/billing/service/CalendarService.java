package com.group2.capstone.EBPaymentSystem.billing.service;


import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class CalendarService {
    public Calendar getDueDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 15);
        return cal;
    }
}
