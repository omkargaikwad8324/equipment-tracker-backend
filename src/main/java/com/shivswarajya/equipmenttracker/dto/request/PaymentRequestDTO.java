package com.shivswarajya.equipmenttracker.dto.request;

import com.shivswarajya.equipmenttracker.enums.PaymentMode;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PaymentRequestDTO {

    @NotNull(message = "Invoice is required")
    private Long invoiceId;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    private Double amount;

    private String transactionReference;

    private String remarks;

}