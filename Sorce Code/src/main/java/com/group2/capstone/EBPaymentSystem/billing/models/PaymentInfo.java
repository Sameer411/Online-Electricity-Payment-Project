package com.group2.capstone.EBPaymentSystem.billing.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="payment_info")
public class PaymentInfo {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String orderId;
	@Column(nullable = true)
	private String paymentId;
	@Column(nullable = true)
	private String signature;
	private String status;
	@OneToOne
	private Bill bill;
	
	
}
