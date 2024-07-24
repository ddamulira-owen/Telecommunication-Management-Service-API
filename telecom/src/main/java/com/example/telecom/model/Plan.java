package com.example.telecom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "plans")
@AllArgsConstructor
@NoArgsConstructor
public class Plan {
    @Id
    private String planID;
    private String name;
    private double price;
    private String description;
    private int validityDays; // Number of days the plan is valid
}
