package com.group2.capstone.EBPaymentSystem.billing.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Meter meter;
    @ManyToOne
    private PropertyType propertyType;
    //    @OneToOne(mappedBy = "property",cascade = CascadeType.ALL)
//    private Bill bill;
    @OneToOne
    private Address address;
}
