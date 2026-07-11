package com.shivswarajya.equipmenttracker.service;

import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.InvoiceRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Invoice;

public interface InvoiceService {

    Invoice createInvoice(InvoiceRequestDTO dto);

    Invoice updateInvoice(Long id, InvoiceRequestDTO dto);

    Invoice getInvoice(Long id);

    List<Invoice> getAllInvoices();

    void deleteInvoice(Long id);

    Invoice getByInvoiceNumber(String invoiceNumber);

    List<Invoice> searchInvoices(String customerName);

    List<Invoice> getInvoicesByDate(LocalDate date);

}