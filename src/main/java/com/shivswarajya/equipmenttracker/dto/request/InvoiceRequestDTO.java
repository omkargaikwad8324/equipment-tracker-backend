package com.shivswarajya.equipmenttracker.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class InvoiceRequestDTO {

    @NotNull(message = "Work Order is required")
    private Long workOrderId;

    private Double gstPercentage = 18.0;

    private Double paidAmount = 0.0;

    private String remarks;

}