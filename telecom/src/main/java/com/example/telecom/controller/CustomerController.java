package com.example.telecom.controller;

import com.example.telecom.model.Customer;
import com.example.telecom.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public Map<String, Object> createCustomer(@RequestBody Customer customer) {
        if (customer.getCustomerID() != null) {
            throw new IllegalArgumentException("ID must not be provided");
        }
        return customerService.createCustomer(customer);
    }

    @GetMapping("/{id}")
    public Map<String, Object> getCustomerById(@PathVariable String id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/name/{name}")
    public List<Customer> getCustomersByName(@PathVariable String name) {
        return customerService.getCustomersByName(name);
    }

    @GetMapping("/email/{email}")
    public List<Customer> getCustomersByEmail(@PathVariable String email) {
        return customerService.getCustomersByEmail(email);
    }

    @GetMapping("/phoneNumber/{phoneNumber}")
    public List<Customer> getCustomersByPhoneNumber(@PathVariable String phoneNumber) {
        return customerService.getCustomersByPhoneNumber(phoneNumber);
    }

    @PutMapping("/{id}")
    public Map<String, Object> updateCustomer(@PathVariable String id, @RequestBody Customer customer) {
        if (customer.getCustomerID() != null) {
            throw new IllegalArgumentException("ID must not be changed in request body");
        }
        return customerService.updateCustomer(id, customer);
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> deleteCustomer(@PathVariable String id) {
        return customerService.deleteCustomer(id);
    }

    @PostMapping("/restore/{id}")
    public Map<String, Object> restoreCustomer(@PathVariable String id) {
        return customerService.restoreCustomer(id);
    }
}
