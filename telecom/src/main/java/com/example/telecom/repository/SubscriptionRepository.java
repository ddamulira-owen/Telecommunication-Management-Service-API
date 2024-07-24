package com.example.telecom.repository;

import com.example.telecom.model.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
    List<Subscription> findByCustomerCustomerID(String customerId);
    List<Subscription> findByPlanPlanID(String planId);
}
