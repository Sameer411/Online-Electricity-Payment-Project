package com.group2.capstone.EBPaymentSystem.billing.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Meter {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long meterId;
	private int meterNo;
}
