package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.PaymentResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Payment;

@Component
public class PaymentMapper {

    public PaymentResponseDTO toResponse(Payment payment) {

        if (payment == null) {
            return null;
        }

        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentNumber(payment.getPaymentNumber())
                .invoiceNumber(payment.getInvoice().getInvoiceNumber())
                .customerName(payment.getInvoice().getCustomer().getName())
                .paymentDate(payment.getPaymentDate())
                .paymentMode(payment.getPaymentMode())
                .amount(payment.getAmount())
                .invoiceTotal(payment.getInvoice().getGrandTotal())
                .paidAmount(payment.getInvoice().getPaidAmount())
                .balanceAmount(payment.getInvoice().getBalanceAmount())
                .transactionReference(payment.getTransactionReference())
                .remarks(payment.getRemarks())
                .build();
    }
}