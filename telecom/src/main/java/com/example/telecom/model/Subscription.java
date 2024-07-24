package com.example.telecom.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String subscriptionID;

    @DBRef
    private Customer customer;
    @DBRef
    private Plan plan;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private String status;
    private double amount; // Amount for this subscription

    public Subscription(Customer customer, Plan plan) {
        this.customer = customer;
        this.plan = plan;
        this.subscriptionStartDate = LocalDate.now();
        this.subscriptionEndDate = LocalDate.now().plusDays(plan.getValidityDays());
        this.status = "active";
        this.amount = plan.getPrice();
    }
}