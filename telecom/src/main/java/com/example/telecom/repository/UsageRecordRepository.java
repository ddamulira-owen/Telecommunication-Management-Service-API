package com.example.telecom.repository;

import com.example.telecom.model.UsageRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UsageRecordRepository extends MongoRepository<UsageRecord, String> {
    List<UsageRecord> findByCustomerCustomerID(String customerID);
    List<UsageRecord> findByPlanPlanID(String planID);
    List<UsageRecord> findByAmount(double amount);
    List<UsageRecord> findByPlanName(String planName);
}
