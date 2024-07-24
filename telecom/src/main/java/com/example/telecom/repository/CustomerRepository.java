package com.example.telecom.repository;

import com.example.telecom.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface CustomerRepository extends MongoRepository<Customer, String> {
    List<Customer> findByName(String name);
    List<Customer> findByEmail(String email);
    List<Customer> findByPhoneNumber(String phoneNumber);
}
