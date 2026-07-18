package com.shivswarajya.equipmenttracker.dto.response;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.PaymentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InvoiceResponseDTO {

    private Long id;

    private String invoiceNumber;

    private LocalDate invoiceDate;

    private String customerName;

    private String workOrderNo;

    // New summary fields
    private Integer totalEquipment;

    private Double subtotal;

    private Double gstAmount;

    private Double grandTotal;

    private Double paidAmount;

    private Double balanceAmount;

    private String remarks;

    private PaymentStatus paymentStatus;
}