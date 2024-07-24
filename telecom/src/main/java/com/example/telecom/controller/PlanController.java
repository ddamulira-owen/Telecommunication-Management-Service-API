package com.example.telecom.controller;

import com.example.telecom.model.Plan;
import com.example.telecom.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping
    public Map<String, Object> createPlan(@RequestBody Plan plan) {
        if (plan.getPlanID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }
        return planService.createPlan(plan);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getPlanById(@PathVariable String id) {
        return planService.getPlanById(id);
    }

    @GetMapping
    public List<Plan> getAllPlans() {
        return planService.getAllPlans();
    }

    @GetMapping("/name/{name}")
    public List<Plan> getPlansByName(@PathVariable String name) {
        return planService.getPlansByName(name);
    }

    @GetMapping("/price/{price}")
    public List<Plan> getPlansByPrice(@PathVariable double price) {
        return planService.getPlansByPrice(price);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updatePlan(@PathVariable String id, @RequestBody Plan plan) {
        if (plan.getPlanID() != null) {
            throw new IllegalArgumentException("ID must not be changed in request body");
        }
        return planService.updatePlan(id, plan);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deletePlan(@PathVariable String id) {
        return planService.deletePlan(id);
    }

    @PostMapping("/restore/{id}")
    public Map<String, Object> restorePlan(@PathVariable String id) {
        return planService.restorePlan(id);
    }
}
