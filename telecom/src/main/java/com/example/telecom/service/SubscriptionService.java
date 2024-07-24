package com.example.telecom.service;

import com.example.telecom.model.Customer;
import com.example.telecom.model.DeletedRecord;
import com.example.telecom.model.Plan;
import com.example.telecom.model.Subscription;
import com.example.telecom.model.UsageRecord;
import com.example.telecom.repository.CustomerRepository;
import com.example.telecom.repository.DeletedRecordRepository;
import com.example.telecom.repository.PlanRepository;
import com.example.telecom.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private DeletedRecordRepository deletedRecordRepository;

    @Autowired
    private UsageRecordService usageRecordService;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public Subscription createSubscription(Subscription subscription) {
        if (subscription.getSubscriptionID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }

        Customer customer = customerRepository.findById(subscription.getCustomer().getCustomerID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

        Plan plan = planRepository.findById(subscription.getPlan().getPlanID())
                .orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));

        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndDate(LocalDate.now().plusDays(plan.getValidityDays()));
        subscription.setStatus("active");
        subscription.setAmount(plan.getPrice());

        String id = String.format("S%03d", counter.incrementAndGet());
        subscription.setSubscriptionID(id);

        customer.setTotalAmount(customer.getTotalAmount() + plan.getPrice());
        customerRepository.save(customer);

        Subscription createdSubscription = subscriptionRepository.save(subscription);
        usageRecordService.createUsageRecord(createdSubscription);

        System.out.println("Subscription created successfully with ID: " + createdSubscription.getSubscriptionID());
        return createdSubscription;
    }

    public Subscription getSubscriptionById(String id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));
        System.out.println("Subscription retrieved successfully: " + subscription);
        return subscription;
    }

    public List<Subscription> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        System.out.println("All subscriptions retrieved successfully");
        return subscriptions;
    }

    public Subscription updateSubscription(String id, Subscription subscription) {
        Subscription existingSubscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        if (subscription.getCustomer() != null && !subscription.getCustomer().getCustomerID().equals(existingSubscription.getCustomer().getCustomerID())) {
            throw new IllegalArgumentException("Customer ID must not be changed");
        }
        if (subscription.getPlan() != null && !subscription.getPlan().getPlanID().equals(existingSubscription.getPlan().getPlanID())) {
            throw new IllegalArgumentException("Plan ID must not be changed");
        }

        existingSubscription.setStatus(subscription.getStatus() != null ? subscription.getStatus() : existingSubscription.getStatus());

        Subscription updatedSubscription = subscriptionRepository.save(existingSubscription);
        usageRecordService.updateUsageRecord(existingSubscription.getSubscriptionID(), updatedSubscription);

        System.out.println("Subscription updated successfully: " + updatedSubscription);
        return updatedSubscription;
    }

    public void deleteSubscription(String id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subscription not found"));

        DeletedRecord deletedRecord = new DeletedRecord("Subscription", subscription);
        deletedRecordRepository.save(deletedRecord);

        Customer customer = subscription.getCustomer();
        customer.setTotalAmount(customer.getTotalAmount() - subscription.getAmount());
        customerRepository.save(customer);

        subscriptionRepository.deleteById(id);
        usageRecordService.deleteUsageRecord(id);

        System.out.println("Subscription deleted successfully with ID: " + id);
    }

    public Subscription restoreSubscription(String id) {
        DeletedRecord deletedRecord = deletedRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Deleted record not found"));

        if (!"Subscription".equals(deletedRecord.getEntityType())) {
            throw new IllegalArgumentException("Invalid entity type");
        }

        Subscription subscription = (Subscription) deletedRecord.getEntityData();
        Customer customer = subscription.getCustomer();
        customer.setTotalAmount(customer.getTotalAmount() + subscription.getAmount());
        customerRepository.save(customer);

        deletedRecordRepository.deleteById(id);
        Subscription restoredSubscription = subscriptionRepository.save(subscription);
        usageRecordService.createUsageRecord(restoredSubscription);

        System.out.println("Subscription restored successfully: " + restoredSubscription);
        return restoredSubscription;
    }

    public List<Subscription> getSubscriptionsByCustomerId(String customerId) {
        List<Subscription> subscriptions = subscriptionRepository.findByCustomerCustomerID(customerId);
        System.out.println("Subscriptions for customer ID " + customerId + " retrieved successfully");
        return subscriptions;
    }

    public List<Subscription> getSubscriptionsByPlanId(String planId) {
        List<Subscription> subscriptions = subscriptionRepository.findByPlanPlanID(planId);
        System.out.println("Subscriptions for plan ID " + planId + " retrieved successfully");
        return subscriptions;
    }
}
