package com.group2.capstone.EBPaymentSystem.billing.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String street;
    private String city;
    private String state;
    private Long postalCode;
    private String country;
    
	@Override
	public String toString() {
		return "Address [street=" + street + ", city=" + city + ", state=" + state + ", postalCode=" + postalCode
				+ ", country=" + country + "]";
	}
    
    
	


}
