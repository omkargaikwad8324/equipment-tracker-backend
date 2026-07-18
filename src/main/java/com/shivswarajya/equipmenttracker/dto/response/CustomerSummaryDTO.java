package com.shivswarajya.equipmenttracker.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerSummaryDTO {

    private Long customerId;

    private String customerName;

    private Double totalRevenue;

    private Double paidAmount;

    private Double pendingAmount;

    private Long totalWorkOrders;

    private Long invoiceCount;

    private Long paymentCount;

}