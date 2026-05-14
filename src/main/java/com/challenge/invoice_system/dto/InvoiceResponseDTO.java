package com.challenge.invoice_system.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponseDTO {
    private Long id;
    private String invoiceNumber;
    private LocalDate date;
    private String customerName;
    private List<InvoiceItemDTO> items;
    private BigDecimal total;
}
