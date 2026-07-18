package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.InvoiceRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.InvoiceResponseDTO;
import com.shivswarajya.equipmenttracker.service.InvoiceService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping
    public ApiResponse<InvoiceResponseDTO> createInvoice(
            @Valid @RequestBody InvoiceRequestDTO dto) {

        return new ApiResponse<>(
                true,
                "Invoice created successfully",
                invoiceService.createInvoice(dto));
    }

    @GetMapping
    public ApiResponse<List<InvoiceResponseDTO>> getAllInvoices() {

        return new ApiResponse<>(
                true,
                "Invoices fetched successfully",
                invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ApiResponse<InvoiceResponseDTO> getInvoice(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Invoice fetched successfully",
                invoiceService.getInvoice(id));
    }

    @PutMapping("/{id}")
    public ApiResponse<InvoiceResponseDTO> updateInvoice(
            @PathVariable Long id,
            @Valid @RequestBody InvoiceRequestDTO dto) {

        return new ApiResponse<>(
                true,
                "Invoice updated successfully",
                invoiceService.updateInvoice(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteInvoice(
            @PathVariable Long id) {

        invoiceService.deleteInvoice(id);

        return new ApiResponse<>(
                true,
                "Invoice deleted successfully",
                null);
    }
}