package com.example.telecom.service;

import com.example.telecom.model.DeletedRecord;
import com.example.telecom.model.Plan;
import com.example.telecom.repository.DeletedRecordRepository;
import com.example.telecom.repository.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private DeletedRecordRepository deletedRecordRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public Map<String, Object> createPlan(Plan plan) {
        if (plan.getPlanID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }
        String id = String.format("P%03d", counter.incrementAndGet());
        plan.setPlanID(id);
        Plan savedPlan = planRepository.save(plan);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Plan created successfully");
        response.put("plan", savedPlan);
        return response;
    }

    public Map<String, Object> getPlanById(String id) {
        Optional<Plan> plan = planRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (plan.isPresent()) {
            response.put("message", "Plan found");
            response.put("plan", plan.get());
        } else {
            response.put("message", "Plan not found");
        }
        return response;
    }

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public List<Plan> getPlansByName(String name) {
        return planRepository.findByName(name);
    }

    public List<Plan> getPlansByPrice(double price) {
        return planRepository.findByPrice(price);
    }

    public Map<String, Object> updatePlan(String id, Plan plan) {
        if (plan.getPlanID() != null) {
            throw new IllegalArgumentException("ID must not be changed");
        }
        Optional<Plan> existingPlan = planRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (existingPlan.isPresent()) {
            Plan updatedPlan = existingPlan.get();
            if (plan.getName() != null) updatedPlan.setName(plan.getName());
            if (plan.getPrice() != 0) updatedPlan.setPrice(plan.getPrice());
            if (plan.getDescription() != null) updatedPlan.setDescription(plan.getDescription());
            if (plan.getValidityDays() != 0) updatedPlan.setValidityDays(plan.getValidityDays());
            planRepository.save(updatedPlan);

            response.put("message", "Plan updated successfully");
            response.put("plan", updatedPlan);
        } else {
            response.put("message", "Plan not found");
        }
        return response;
    }

    public Map<String, Object> deletePlan(String id) {
        Optional<Plan> plan = planRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (plan.isPresent()) {
            DeletedRecord deletedRecord = new DeletedRecord("Plan", plan.get());
            deletedRecordRepository.save(deletedRecord);
            planRepository.deleteById(id);

            response.put("message", "Plan deleted successfully");
        } else {
            response.put("message", "Plan not found");
        }
        return response;
    }

    public Map<String, Object> restorePlan(String id) {
        Optional<DeletedRecord> deletedRecord = deletedRecordRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (deletedRecord.isPresent() && "Plan".equals(deletedRecord.get().getEntityType())) {
            Plan plan = (Plan) deletedRecord.get().getEntityData();
            deletedRecordRepository.deleteById(id);
            planRepository.save(plan);

            response.put("message", "Plan restored successfully");
            response.put("plan", plan);
        } else {
            response.put("message", "Deleted record not found or not a Plan");
        }
        return response;
    }
}
