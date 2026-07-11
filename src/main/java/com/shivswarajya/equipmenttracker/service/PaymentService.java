package com.shivswarajya.equipmenttracker.service;

import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.PaymentRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Payment;

public interface PaymentService {

    Payment addPayment(PaymentRequestDTO dto);

    Payment getPayment(Long id);

    List<Payment> getAllPayments();

    List<Payment> getPaymentsByInvoice(Long invoiceId);

    void deletePayment(Long id);

    Payment getByPaymentNumber(String paymentNumber);

    List<Payment> getPaymentsByDate(LocalDate date);

}