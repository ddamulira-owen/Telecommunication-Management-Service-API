package com.example.telecom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "usageRecords")
@AllArgsConstructor
@NoArgsConstructor
public class UsageRecord {
    @Id
    private String usageRecordID;
    private Customer customer;
    private Plan plan;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private String status;
    private double amount;
    private double totalAmount;
}
