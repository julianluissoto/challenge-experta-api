package com.challenge.invoice_system.dto;

import com.challenge.invoice_system.model.InvoiceItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequestDTO {
    private Long customerId;
    private List<InvoiceItem> items;

}
