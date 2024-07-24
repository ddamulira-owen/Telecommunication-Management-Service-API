package com.example.telecom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "deletedRecords")
public class DeletedRecord {

    @Id
    private String recordID;

    private String entityType; // "Customer", "Plan", "Subscription", "UsageRecord"
    private Object entityData;

    public DeletedRecord(String entityType, Object entityData) {
        this.entityType = entityType;
        this.entityData = entityData;
    }
}
