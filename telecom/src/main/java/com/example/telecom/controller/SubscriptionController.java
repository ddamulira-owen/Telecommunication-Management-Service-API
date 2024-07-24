package com.example.telecom.controller;

import com.example.telecom.model.Subscription;
import com.example.telecom.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping
    public String createSubscription(@RequestBody Subscription subscription) {
        if (subscription.getSubscriptionID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }
        Subscription createdSubscription = subscriptionService.createSubscription(subscription);
        return "Subscription created successfully with ID: " + createdSubscription.getSubscriptionID();
    }

    @GetMapping("/{id}")
    public Subscription getSubscriptionById(@PathVariable String id) {
        return subscriptionService.getSubscriptionById(id);
    }

    @GetMapping
    public List<Subscription> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    @PutMapping("/{id}")
    public String updateSubscription(@PathVariable String id, @RequestBody Subscription subscription) {
        if (subscription.getSubscriptionID() != null) {
            throw new IllegalArgumentException("ID must not be changed in request body");
        }
        Subscription updatedSubscription = subscriptionService.updateSubscription(id, subscription);
        return "Subscription updated successfully: " + updatedSubscription;
    }

    @DeleteMapping("/{id}")
    public String deleteSubscription(@PathVariable String id) {
        subscriptionService.deleteSubscription(id);
        return "Subscription deleted successfully with ID: " + id;
    }

    @PostMapping("/restore/{id}")
    public String restoreSubscription(@PathVariable String id) {
        Subscription restoredSubscription = subscriptionService.restoreSubscription(id);
        return "Subscription restored successfully: " + restoredSubscription;
    }

    @GetMapping("/customer/{customerId}")
    public List<Subscription> getSubscriptionsByCustomerId(@PathVariable String customerId) {
        return subscriptionService.getSubscriptionsByCustomerId(customerId);
    }

    @GetMapping("/plan/{planId}")
    public List<Subscription> getSubscriptionsByPlanId(@PathVariable String planId) {
        return subscriptionService.getSubscriptionsByPlanId(planId);
    }
}
