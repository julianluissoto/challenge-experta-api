package com.challenge.invoice_system.service;

import com.challenge.invoice_system.exception.BusinessException;
import com.challenge.invoice_system.model.Customer;
import com.challenge.invoice_system.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public Customer save(Customer customer) {

        String email = customer.getEmail().trim().toLowerCase();
        customer.setEmail(email);

        if (customerRepository.existsByEmail(email)) {
            throw new BusinessException("El email '" + email + "' ya está registrado con otro cliente.");
        }

        return customerRepository.save(customer);
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}