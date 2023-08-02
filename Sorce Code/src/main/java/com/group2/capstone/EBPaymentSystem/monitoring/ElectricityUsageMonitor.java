package com.group2.capstone.EBPaymentSystem.monitoring;

import java.util.List;

public interface ElectricityUsageMonitor {
    double getCurrentUsage();

    List<Double> getHistoricalUsage();
}

