package com.group2.capstone.EBPaymentSystem.billing.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "bills")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDate billingMonth;
    private Date dueDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Property property;
    private Integer status;
    private Double unitsConsumed;

    public Bill(double amount, Date dueDate) {
        this.amount = amount;
        this.dueDate = dueDate;
    }


    // getters and setters
}
