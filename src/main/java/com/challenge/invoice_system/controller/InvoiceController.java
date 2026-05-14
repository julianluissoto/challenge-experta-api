package com.challenge.invoice_system.controller;

import com.challenge.invoice_system.dto.InvoiceResponseDTO;
import com.challenge.invoice_system.model.Invoice;
import com.challenge.invoice_system.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceResponseDTO> createInvoice(@RequestBody Invoice invoice) {

        InvoiceResponseDTO savedInvoice = invoiceService.saveInvoice(invoice);
        return new ResponseEntity<>(savedInvoice, HttpStatus.CREATED);

    }
}