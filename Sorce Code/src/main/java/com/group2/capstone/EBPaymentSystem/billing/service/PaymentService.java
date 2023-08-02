package com.group2.capstone.EBPaymentSystem.billing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group2.capstone.EBPaymentSystem.billing.models.PaymentInfo;
import com.group2.capstone.EBPaymentSystem.billing.repository.PaymentInfoRepo;

@Service
public class PaymentService {

	@Autowired
	private PaymentInfoRepo paymentInfoRepo;
	
	public void insertPaymentInfo(PaymentInfo paymentInfo) {
		
		paymentInfoRepo.save(paymentInfo);
	}

	public PaymentInfo findByOrderId(String orderId) {
		PaymentInfo paymentInfo = paymentInfoRepo.findByOrderId(orderId);
		return paymentInfo;
	}

	public void updatePaymentInfo(String paymentId, String signature, String  status, String orderId) {
		PaymentInfo paymentInfo = findByOrderId(orderId);
		paymentInfo.setPaymentId(paymentId);
		paymentInfo.setSignature(signature);
		paymentInfo.setStatus(status);
		
		insertPaymentInfo(paymentInfo);
	}

}
