package com.group2.capstone.EBPaymentSystem.billing.models;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "meter_readings")
public class MeterReadings {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	private LocalDate date;
	private double unitsConsumed;
	@ManyToOne
	private Meter meter;
	
}
