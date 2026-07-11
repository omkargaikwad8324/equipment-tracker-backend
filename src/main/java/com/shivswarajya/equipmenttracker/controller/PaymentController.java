package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.PaymentRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.PaymentResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Payment;
import com.shivswarajya.equipmenttracker.mapper.PaymentMapper;
import com.shivswarajya.equipmenttracker.service.PaymentService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PaymentController {

    private final PaymentService paymentService;
    private final PaymentMapper paymentMapper;

    @PostMapping
    public ApiResponse<PaymentResponseDTO> addPayment(
            @Valid @RequestBody PaymentRequestDTO dto) {

        Payment payment = paymentService.addPayment(dto);

        return new ApiResponse<>(
                true,
                "Payment added successfully",
                paymentMapper.toResponse(payment));
    }

    @GetMapping
    public ApiResponse<List<PaymentResponseDTO>> getAllPayments() {

        List<PaymentResponseDTO> response = paymentService.getAllPayments()
                .stream()
                .map(paymentMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Payments fetched successfully",
                response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentResponseDTO> getPayment(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Payment fetched successfully",
                paymentMapper.toResponse(paymentService.getPayment(id)));
    }

    @GetMapping("/invoice/{invoiceId}")
    public ApiResponse<List<PaymentResponseDTO>> getPaymentsByInvoice(
            @PathVariable Long invoiceId) {

        List<PaymentResponseDTO> response = paymentService
                .getPaymentsByInvoice(invoiceId)
                .stream()
                .map(paymentMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Invoice payments fetched successfully",
                response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deletePayment(
            @PathVariable Long id) {

        paymentService.deletePayment(id);

        return new ApiResponse<>(
                true,
                "Payment deleted successfully",
                null);
    }
}