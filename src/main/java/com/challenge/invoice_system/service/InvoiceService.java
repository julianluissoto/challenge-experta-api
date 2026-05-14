package com.challenge.invoice_system.service;

import com.challenge.invoice_system.dto.InvoiceItemDTO;
import com.challenge.invoice_system.dto.InvoiceResponseDTO;
import com.challenge.invoice_system.exception.BusinessException;
import com.challenge.invoice_system.model.Customer;
import com.challenge.invoice_system.model.Invoice;
import com.challenge.invoice_system.model.InvoiceItem;
import com.challenge.invoice_system.repository.CustomerRepository;
import com.challenge.invoice_system.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public InvoiceResponseDTO saveInvoice(Invoice request) {

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() -> new BusinessException("El cliente con ID " + request.getCustomerId() + " no existe."));


        long nextId = invoiceRepository.count() + 1;
        String generatedNumber = String.format("FACTURA-%04d", nextId);
        request.setInvoiceNumber(generatedNumber);

        Set<String> productNames = new HashSet<>();
        BigDecimal totalGeneral = BigDecimal.ZERO;

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new BusinessException("La factura debe tener al menos un producto.");
        }

        for (InvoiceItem item : request.getItems()) {
            String productName = item.getProductName().trim().toLowerCase();

            if (productNames.contains(productName)) {
                throw new BusinessException("El producto '" + item.getProductName() + "' ya fue agregado.");
            }
            productNames.add(productName);

            if (item.getUnitPrice() != null && item.getQuantity() != null) {
                BigDecimal subtotal = item.getUnitPrice().multiply(new BigDecimal(item.getQuantity()));
                item.setSubtotal(subtotal);
                totalGeneral = totalGeneral.add(subtotal);
            }

            item.setInvoice(request);
        }

        request.setTotal(totalGeneral);
        request.setDate(LocalDate.now());

        Invoice savedInvoice = invoiceRepository.save(request);

        return convertToDTO(savedInvoice, customer.getName());
    }

    private InvoiceResponseDTO convertToDTO(Invoice invoice, String customerName) {
        InvoiceResponseDTO dto = new InvoiceResponseDTO();
        dto.setId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber());
        dto.setTotal(invoice.getTotal());
        dto.setDate(invoice.getDate());
        dto.setCustomerName(customerName);

        List<InvoiceItemDTO> itemDTOs = invoice.getItems().stream().map(item -> {
            InvoiceItemDTO iDto = new InvoiceItemDTO();
            iDto.setProductName(item.getProductName());
            iDto.setQuantity(item.getQuantity());
            iDto.setUnitPrice(item.getUnitPrice());
            iDto.setSubtotal(item.getSubtotal());
            return iDto;
        }).toList();

        dto.setItems(itemDTOs);
        return dto;
    }
}