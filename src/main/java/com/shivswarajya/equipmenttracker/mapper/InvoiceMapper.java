package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.InvoiceResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Invoice;

@Component
public class InvoiceMapper {

    public InvoiceResponseDTO toResponse(Invoice invoice) {

        if (invoice == null) {
            return null;
        }

        return InvoiceResponseDTO.builder()
                .id(invoice.getId())
                .invoiceNumber(invoice.getInvoiceNumber())
                .invoiceDate(invoice.getInvoiceDate())
                .customerName(invoice.getCustomer().getName())
                .workOrderNo(invoice.getWorkOrder().getWorkOrderNo())
                .totalEquipment(
                        invoice.getWorkOrder().getItems() != null
                                ? invoice.getWorkOrder().getItems().size()
                                : 0)
                .subtotal(invoice.getSubtotal())
                .gstAmount(invoice.getGstAmount())
                .grandTotal(invoice.getGrandTotal())
                .paidAmount(invoice.getPaidAmount())
                .balanceAmount(invoice.getBalanceAmount())
                .paymentStatus(invoice.getPaymentStatus())
                .remarks(invoice.getRemarks())
                .build();
    }
}