package com.group2.capstone.EBPaymentSystem.billing.repository;

import com.group2.capstone.EBPaymentSystem.billing.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface BillingRepo extends JpaRepository<Bill, Long> {

    @Query(value="select * from bills where property_id = ?1 order by billing_month desc",nativeQuery = true)
    List<Bill> findByProperty(Long id);


    @Query(value="select * from bills where property_id = ?1 and month(billing_month)=?2 and year(billing_month)=?3",nativeQuery = true)
    Bill findByPropertyAndDate(Long id, int month, int year);

}
