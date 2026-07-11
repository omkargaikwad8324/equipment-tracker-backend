package com.shivswarajya.equipmenttracker.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.PaymentStatus;

@Data
@Builder
public class InvoiceResponseDTO {

    private Long id;

    private String invoiceNumber;

    private LocalDate invoiceDate;

    private String customerName;

    private String workOrderNo;

    private String equipmentName;

    private Double totalHours;

    private Double subtotal;

    private Double gstAmount;

    private Double grandTotal;

    private Double paidAmount;

    private Double balanceAmount;

    private String remarks;

    private PaymentStatus paymentStatus;

}