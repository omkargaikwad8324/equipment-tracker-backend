package com.shivswarajya.equipmenttracker.service;

import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.InvoiceRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.InvoiceResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Invoice;

public interface InvoiceService {

    InvoiceResponseDTO createInvoice(InvoiceRequestDTO dto);

    InvoiceResponseDTO getInvoice(Long id);

    List<InvoiceResponseDTO> getAllInvoices();

    InvoiceResponseDTO updateInvoice(Long id, InvoiceRequestDTO dto);

    void deleteInvoice(Long id);

    InvoiceResponseDTO getByInvoiceNumber(String invoiceNumber);

    List<InvoiceResponseDTO> searchInvoices(String customerName);

    List<InvoiceResponseDTO> getInvoicesByDate(LocalDate date);

    double getTotalRevenue();
}