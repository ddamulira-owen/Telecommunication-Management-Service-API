package com.example.telecom.repository;

import com.example.telecom.model.Plan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlanRepository extends MongoRepository<Plan, String> {
    List<Plan> findByName(String name);
    List<Plan> findByPrice(double price);
}
