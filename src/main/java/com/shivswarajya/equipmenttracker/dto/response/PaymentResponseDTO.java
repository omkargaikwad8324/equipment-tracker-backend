package com.shivswarajya.equipmenttracker.dto.response;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.PaymentMode;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDTO {

    private Long id;

    private String paymentNumber;

    private String invoiceNumber;

    private String customerName;

    private LocalDate paymentDate;

    private PaymentMode paymentMode;

    private Double amount;

    private Double invoiceTotal;

    private Double paidAmount;

    private Double balanceAmount;

    private String transactionReference;

    private String remarks;

}