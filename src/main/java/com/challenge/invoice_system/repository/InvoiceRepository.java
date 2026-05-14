package com.challenge.invoice_system.repository;

import com.challenge.invoice_system.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
