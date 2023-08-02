package com.group2.capstone.EBPaymentSystem.billing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentPageController {

	
	@GetMapping("/pay")
	public String getPaymentPage() {
		return "payment";
	}
	
	@GetMapping("/success")
	public String successPage() {
		return "success";
	}
}
