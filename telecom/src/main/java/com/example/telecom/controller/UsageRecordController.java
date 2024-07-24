package com.example.telecom.controller;

import com.example.telecom.model.UsageRecord;
import com.example.telecom.service.UsageRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usageRecords")
public class UsageRecordController {

    @Autowired
    private UsageRecordService usageRecordService;

    @GetMapping("/{id}")
    public UsageRecord getUsageRecordById(@PathVariable String id) {
        return usageRecordService.getUsageRecordById(id);
    }

    @GetMapping("/customer/{customerId}")
    public List<UsageRecord> getUsageRecordsByCustomerId(@PathVariable String customerId) {
        return usageRecordService.getUsageRecordsByCustomerId(customerId);
    }

    @GetMapping("/plan/{planId}")
    public List<UsageRecord> getUsageRecordsByPlanId(@PathVariable String planId) {
        return usageRecordService.getUsageRecordsByPlanId(planId);
    }

    @GetMapping("/amount/{amount}")
    public List<UsageRecord> getUsageRecordsByAmount(@PathVariable double amount) {
        return usageRecordService.getUsageRecordsByAmount(amount);
    }

    @GetMapping("/planName/{planName}")
    public List<UsageRecord> getUsageRecordsByPlanName(@PathVariable String planName) {
        return usageRecordService.getUsageRecordsByPlanName(planName);
    }

    @GetMapping
    public List<UsageRecord> getAllUsageRecords() {
        return usageRecordService.getAllUsageRecords();
    }

    @DeleteMapping("/{id}")
    public String deleteUsageRecord(@PathVariable String id) {
        usageRecordService.deleteUsageRecord(id);
        return "Usage record deleted successfully with ID: " + id;
    }
}
