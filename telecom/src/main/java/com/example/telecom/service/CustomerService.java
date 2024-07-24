package com.example.telecom.service;

import com.example.telecom.model.Customer;
import com.example.telecom.model.DeletedRecord;
import com.example.telecom.repository.CustomerRepository;
import com.example.telecom.repository.DeletedRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DeletedRecordRepository deletedRecordRepository;

    private static final AtomicInteger counter = new AtomicInteger(0);

    public Map<String, Object> createCustomer(Customer customer) {
        if (customer.getCustomerID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }
        String id = String.format("C%03d", counter.incrementAndGet());
        customer.setCustomerID(id);
        Customer savedCustomer = customerRepository.save(customer);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Customer created successfully");
        response.put("customer", savedCustomer);
        return response;
    }

    public Map<String, Object> getCustomerById(String id) {
        Optional<Customer> customer = customerRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (customer.isPresent()) {
            response.put("message", "Customer found");
            response.put("customer", customer.get());
        } else {
            response.put("message", "Customer not found");
        }
        return response;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public List<Customer> getCustomersByName(String name) {
        return customerRepository.findByName(name);
    }

    public List<Customer> getCustomersByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public List<Customer> getCustomersByPhoneNumber(String phoneNumber) {
        return customerRepository.findByPhoneNumber(phoneNumber);
    }

    public Map<String, Object> updateCustomer(String id, Customer customer) {
        if (customer.getCustomerID() != null) {
            throw new IllegalArgumentException("ID must not be changed");
        }
        Optional<Customer> existingCustomer = customerRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (existingCustomer.isPresent()) {
            Customer updatedCustomer = existingCustomer.get();
            if (customer.getName() != null) updatedCustomer.setName(customer.getName());
            if (customer.getEmail() != null) updatedCustomer.setEmail(customer.getEmail());
            if (customer.getPhoneNumber() != null) updatedCustomer.setPhoneNumber(customer.getPhoneNumber());
            customerRepository.save(updatedCustomer);

            response.put("message", "Customer updated successfully");
            response.put("customer", updatedCustomer);
        } else {
            response.put("message", "Customer not found");
        }
        return response;
    }

    public Map<String, Object> deleteCustomer(String id) {
        Optional<Customer> customer = customerRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (customer.isPresent()) {
            DeletedRecord deletedRecord = new DeletedRecord("Customer", customer.get());
            deletedRecordRepository.save(deletedRecord);
            customerRepository.deleteById(id);

            response.put("message", "Customer deleted successfully");
        } else {
            response.put("message", "Customer not found");
        }
        return response;
    }

    public Map<String, Object> restoreCustomer(String id) {
        Optional<DeletedRecord> deletedRecord = deletedRecordRepository.findById(id);

        Map<String, Object> response = new HashMap<>();
        if (deletedRecord.isPresent() && "Customer".equals(deletedRecord.get().getEntityType())) {
            Customer customer = (Customer) deletedRecord.get().getEntityData();
            deletedRecordRepository.deleteById(id);
            customerRepository.save(customer);

            response.put("message", "Customer restored successfully");
            response.put("customer", customer);
        } else {
            response.put("message", "Deleted record not found or not a Customer");
        }
        return response;
    }
}
