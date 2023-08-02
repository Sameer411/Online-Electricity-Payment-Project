package com.group2.capstone.EBPaymentSystem.billing.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
	
	
	private String orderId;
	private Integer amount;
	private String currency;
	private long billId;

}
