package com.shivswarajya.equipmenttracker.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shivswarajya.equipmenttracker.dto.request.PaymentRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Invoice;
import com.shivswarajya.equipmenttracker.entity.Payment;
import com.shivswarajya.equipmenttracker.enums.PaymentStatus;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.PaymentRepository;
import com.shivswarajya.equipmenttracker.service.PaymentService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;

    @Override
    public Payment addPayment(PaymentRequestDTO dto) {

Invoice invoice = invoiceRepository.findByIdWithDetails(dto.getInvoiceId())
        .orElseThrow(() ->
                new ResourceNotFoundException("Invoice not found"));

        if (invoice.getBalanceAmount() <= 0) {
            throw new BadRequestException("Invoice already fully paid.");
        }
        if (dto.getAmount() <= 0) {
            throw new BadRequestException(
                    "Payment amount must be greater than zero.");
        }

        if (dto.getAmount() > invoice.getBalanceAmount()) {
            throw new BadRequestException(
                    "Payment amount exceeds pending balance.");
        }

        Payment payment = Payment.builder()
                .paymentNumber(generatePaymentNumber())
                .invoice(invoice)
                .paymentDate(LocalDate.now())
                .paymentMode(dto.getPaymentMode())
                .amount(dto.getAmount())
                .transactionReference(dto.getTransactionReference())
                .remarks(dto.getRemarks())
                .build();

        updateInvoiceAmounts(invoice, dto.getAmount());

        invoiceRepository.save(invoice);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long id) {

        return paymentRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
    }

    @Override
    public List<Payment> getAllPayments() {

        return paymentRepository.findAllWithDetails();
    }

    @Override
    public List<Payment> getPaymentsByInvoice(Long invoiceId) {

        return paymentRepository.findByInvoiceIdWithDetails(invoiceId);
    }

    @Override
    public void deletePayment(Long id) {

        Payment payment = getPayment(id);

        Invoice invoice = payment.getInvoice();

        reversePayment(invoice, payment.getAmount());

        invoiceRepository.save(invoice);

        paymentRepository.delete(payment);
    }

    @Override
    public Payment getByPaymentNumber(String paymentNumber) {

        return paymentRepository.findByPaymentNumber(paymentNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Payment not found"));
    }

    @Override
    public List<Payment> getPaymentsByDate(LocalDate date) {

        return paymentRepository.findByPaymentDate(date);
    }

    private String generatePaymentNumber() {

        Optional<Payment> last = paymentRepository.findTopByOrderByIdDesc();

        if (last.isEmpty()) {
            return "PAY-000001";
        }

        String lastNo = last.get().getPaymentNumber();

        int number = Integer.parseInt(lastNo.substring(4));

        return String.format("PAY-%06d", number + 1);
    }

    private PaymentStatus calculatePaymentStatus(
            double paidAmount,
            double balanceAmount) {

        if (paidAmount <= 0) {
            return PaymentStatus.PENDING;
        }

        if (balanceAmount <= 0) {
            return PaymentStatus.PAID;
        }

        return PaymentStatus.PARTIAL;
    }

    private void updateInvoiceAmounts(
            Invoice invoice,
            double paymentAmount) {

        invoice.setPaidAmount(
                invoice.getPaidAmount() + paymentAmount);

        invoice.setBalanceAmount(
                invoice.getGrandTotal() - invoice.getPaidAmount());

        // Prevent floating-point precision issues
        if (invoice.getBalanceAmount() < 0.01) {
            invoice.setBalanceAmount(0.0);
        }

        invoice.setPaymentStatus(
                calculatePaymentStatus(
                        invoice.getPaidAmount(),
                        invoice.getBalanceAmount()));
    }

    private void reversePayment(
            Invoice invoice,
            double paymentAmount) {

        invoice.setPaidAmount(
                invoice.getPaidAmount() - paymentAmount);

        if (invoice.getPaidAmount() < 0) {
            invoice.setPaidAmount(0.0);
        }

        invoice.setBalanceAmount(
                invoice.getGrandTotal() - invoice.getPaidAmount());

        if (invoice.getBalanceAmount() < 0.01) {
            invoice.setBalanceAmount(0.0);
        }

        invoice.setPaymentStatus(
                calculatePaymentStatus(
                        invoice.getPaidAmount(),
                        invoice.getBalanceAmount()));
    }
}