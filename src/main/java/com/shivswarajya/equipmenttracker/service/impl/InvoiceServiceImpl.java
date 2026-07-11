package com.shivswarajya.equipmenttracker.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shivswarajya.equipmenttracker.dto.request.InvoiceRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Invoice;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.enums.PaymentStatus;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.service.InvoiceService;
import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final WorkOrderRepository workOrderRepository;

    @Override
    public Invoice createInvoice(InvoiceRequestDTO dto) {

        validateInvoiceAlreadyExists(dto.getWorkOrderId());
        WorkOrder workOrder = workOrderRepository.findById(dto.getWorkOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));

        if (workOrder.getStatus() == WorkStatus.CANCELLED) {
            throw new BadRequestException(
                    "Cannot generate invoice for a cancelled work order.");
        }
        if (workOrder.getStatus() == WorkStatus.PENDING) {
            throw new BadRequestException(
                    "Complete the Work Order before generating an invoice.");
        }

        if (workOrder.getTotalAmount() == null) {
            throw new BadRequestException(
                    "Work Order amount is not calculated.");
        }

        if (dto.getPaidAmount() < 0) {
            throw new BadRequestException(
                    "Paid amount cannot be negative.");
        }
        if (dto.getGstPercentage() < 0) {
            throw new BadRequestException(
                    "GST percentage cannot be negative.");
        }
        double subtotal = workOrder.getTotalAmount().doubleValue();
        double gstAmount = calculateGST(
                subtotal,
                dto.getGstPercentage());
        double grandTotal = calculateGrandTotal(
                subtotal,
                gstAmount);
        if (dto.getPaidAmount() > grandTotal) {
            throw new BadRequestException(
                    "Paid amount cannot exceed Grand Total.");
        }
        if (dto.getPaidAmount() < 0) {
            throw new BadRequestException(
                    "Paid amount cannot be negative.");
        }

        double paid = dto.getPaidAmount();

        double balance = grandTotal - paid;

        PaymentStatus paymentStatus = calculatePaymentStatus(
                paid,
                balance);

        Invoice invoice = Invoice.builder()
                .invoiceNumber(generateInvoiceNumber())
                .invoiceDate(LocalDate.now())
                .customer(workOrder.getCustomer())
                .workOrder(workOrder)
                .subtotal(subtotal)
                .gstAmount(gstAmount)
                .grandTotal(grandTotal)
                .paidAmount(paid)
                .balanceAmount(balance)
                .paymentStatus(paymentStatus)
                .remarks(dto.getRemarks())
                .build();

        Invoice saved = invoiceRepository.save(invoice);

        workOrder.setInvoice(saved);

        workOrderRepository.save(workOrder);

        return saved;
    }

    @Override
    public Invoice getInvoice(Long id) {

       return invoiceRepository.findByIdWithDetails(id)
        .orElseThrow(() ->
                new ResourceNotFoundException("Invoice not found"));
    }

    @Override
    public List<Invoice> getAllInvoices() {

        return invoiceRepository.findAll();
    }

    @Override
    public void deleteInvoice(Long id) {

        Invoice invoice = getInvoice(id);

        WorkOrder workOrder = invoice.getWorkOrder();

        if (workOrder != null) {
            workOrder.setInvoice(null);
            workOrderRepository.save(workOrder);
        }

        invoiceRepository.delete(invoice);
    }

    private String generateInvoiceNumber() {

        Optional<Invoice> lastInvoice = invoiceRepository.findTopByOrderByIdDesc();

        if (lastInvoice.isEmpty()) {
            return "INV-000001";
        }

        String last = lastInvoice.get().getInvoiceNumber();

        int number = Integer.parseInt(last.substring(4));

        return String.format("INV-%06d", number + 1);
    }

    private double calculateGST(double subtotal,
            double percentage) {

        return subtotal * percentage / 100.0;
    }

    private double calculateGrandTotal(
            double subtotal,
            double gst) {

        return subtotal + gst;
    }

    private void validateInvoiceAlreadyExists(Long workOrderId) {
        if (invoiceRepository.existsByWorkOrderId(workOrderId)) {
            throw new BadRequestException(
                    "Invoice already exists for this Work Order.");
        }
    }

    private PaymentStatus calculatePaymentStatus(
            double paid,
            double balance) {

        if (paid <= 0) {
            return PaymentStatus.PENDING;
        }

        if (balance <= 0) {
            return PaymentStatus.PAID;
        }

        return PaymentStatus.PARTIAL;
    }


    public double getTotalRevenue() {

        return invoiceRepository.findAll()
                .stream()
                .mapToDouble(Invoice::getGrandTotal)
                .sum();
    }

    @Override
    public Invoice updateInvoice(Long id, InvoiceRequestDTO dto) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found"));

        WorkOrder workOrder = workOrderRepository.findById(dto.getWorkOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));

        if (workOrder.getTotalAmount() == null) {
            throw new BadRequestException("Work Order amount is not calculated.");
        }

        double subtotal = workOrder.getTotalAmount().doubleValue();

        if (dto.getGstPercentage() < 0) {
            throw new BadRequestException(
                    "GST percentage cannot be negative.");
        }
        double gstAmount = calculateGST(
                subtotal,
                dto.getGstPercentage());

        double grandTotal = calculateGrandTotal(
                subtotal,
                gstAmount);

        if (dto.getPaidAmount() > grandTotal) {
            throw new BadRequestException(
                    "Paid amount cannot exceed Grand Total.");
        }

        double balance = grandTotal - dto.getPaidAmount();

        PaymentStatus paymentStatus = calculatePaymentStatus(
                dto.getPaidAmount(),
                balance);

        invoice.setWorkOrder(workOrder);
        workOrder.setInvoice(invoice);
        workOrderRepository.save(workOrder);
        invoice.setCustomer(workOrder.getCustomer());

        invoice.setSubtotal(subtotal);
        invoice.setGstAmount(gstAmount);
        invoice.setGrandTotal(grandTotal);

        invoice.setPaidAmount(dto.getPaidAmount());
        invoice.setBalanceAmount(balance);

        invoice.setPaymentStatus(paymentStatus);

        invoice.setRemarks(dto.getRemarks());

        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getByInvoiceNumber(String invoiceNumber) {

        return invoiceRepository
                .findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Invoice not found"));
    }

    @Override
    public List<Invoice> searchInvoices(String customerName) {

        return invoiceRepository
                .findByCustomer_NameContainingIgnoreCase(customerName);
    }

    @Override
    public List<Invoice> getInvoicesByDate(LocalDate date) {
        return invoiceRepository.findByInvoiceDate(date);
    }
}