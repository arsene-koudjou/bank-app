package com.example.hexagonal_bank.controller;

import com.example.hexagonal_bank.model.Customer;
import com.example.hexagonal_bank.repository.CustomerRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer Management", description = "Endpoints for managing customers")
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    @Operation(summary = "Get all customers")
    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }

    @Operation(summary = "Create a new customer")
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer){
        return customerRepository.save(customer);
    }
}
