package com.example.telecom.service;

import com.example.telecom.model.UsageRecord;
import com.example.telecom.model.Subscription;
import com.example.telecom.repository.UsageRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsageRecordService {

    @Autowired
    private UsageRecordRepository usageRecordRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public UsageRecord createUsageRecord(Subscription subscription) {
        UsageRecord usageRecord = new UsageRecord();
        String id = String.format("U%03d", counter.incrementAndGet());
        usageRecord.setUsageRecordID(id);
        usageRecord.setCustomer(subscription.getCustomer());
        usageRecord.setPlan(subscription.getPlan());
        usageRecord.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
        usageRecord.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
        usageRecord.setStatus(subscription.getStatus());
        usageRecord.setAmount(subscription.getAmount());
        usageRecord.setTotalAmount(subscription.getAmount());
        return usageRecordRepository.save(usageRecord);
    }

    public UsageRecord updateUsageRecord(String id, Subscription subscription) {
        Optional<UsageRecord> existingUsageRecord = usageRecordRepository.findById(id);
        if (existingUsageRecord.isPresent()) {
            UsageRecord usageRecord = existingUsageRecord.get();
            usageRecord.setCustomer(subscription.getCustomer());
            usageRecord.setPlan(subscription.getPlan());
            usageRecord.setSubscriptionStartDate(subscription.getSubscriptionStartDate());
            usageRecord.setSubscriptionEndDate(subscription.getSubscriptionEndDate());
            usageRecord.setStatus(subscription.getStatus());
            usageRecord.setAmount(subscription.getAmount());
            usageRecord.setTotalAmount(subscription.getAmount());
            return usageRecordRepository.save(usageRecord);
        } else {
            throw new IllegalArgumentException("Usage record not found");
        }
    }

    public UsageRecord getUsageRecordById(String id) {
        return usageRecordRepository.findById(id).orElse(null);
    }

    public List<UsageRecord> getUsageRecordsByCustomerId(String customerId) {
        return usageRecordRepository.findByCustomerCustomerID(customerId);
    }

    public List<UsageRecord> getUsageRecordsByPlanId(String planId) {
        return usageRecordRepository.findByPlanPlanID(planId);
    }

    public List<UsageRecord> getUsageRecordsByAmount(double amount) {
        return usageRecordRepository.findByAmount(amount);
    }

    public List<UsageRecord> getUsageRecordsByPlanName(String planName) {
        return usageRecordRepository.findByPlanName(planName);
    }

    public List<UsageRecord> getAllUsageRecords() {
        return usageRecordRepository.findAll();
    }

    public void deleteUsageRecord(String id) {
        usageRecordRepository.deleteById(id);
    }
}
