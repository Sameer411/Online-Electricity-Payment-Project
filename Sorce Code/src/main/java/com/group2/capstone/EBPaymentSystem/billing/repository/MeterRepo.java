package com.group2.capstone.EBPaymentSystem.billing.repository;

import com.group2.capstone.EBPaymentSystem.billing.models.Meter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeterRepo extends JpaRepository<Meter, Long> {
}
