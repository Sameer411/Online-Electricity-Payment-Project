package com.group2.capstone.EBPaymentSystem.profile;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class MeterService {
    Double currentDayUsage;
    Map<Date, Double> historicalUsage;

    //    List<Double> historicalUsage;
    public double getMonthUsage(int year, int month) {
        double monthUsage = 0;
        Calendar cal = Calendar.getInstance();
        for (Map.Entry<Date, Double> entry : historicalUsage.entrySet()) {
            cal.setTime(entry.getKey());
            if (cal.get(Calendar.YEAR) == year && cal.get(Calendar.MONTH) == month) {
                monthUsage += entry.getValue();
            }
        }
        return monthUsage;
    }
}
