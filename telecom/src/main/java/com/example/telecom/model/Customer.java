package com.example.telecom.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "customers")
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    private String customerID;

    private String name;
    private String email;
    private String phoneNumber;
    private double totalAmount;

    public Customer(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.totalAmount = 0.0;
    }
}