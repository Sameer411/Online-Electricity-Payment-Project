package com.group2.capstone.EBPaymentSystem.billing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.group2.capstone.EBPaymentSystem.billing.models.PaymentInfo;

@Repository
public interface PaymentInfoRepo extends JpaRepository<PaymentInfo, Long> {

	@Query(value="select * from payment_info where order_id = ?1",nativeQuery = true)
	PaymentInfo findByOrderId(String orderId);

}
