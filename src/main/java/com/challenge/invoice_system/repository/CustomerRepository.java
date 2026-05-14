package com.challenge.invoice_system.repository;

import com.challenge.invoice_system.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}