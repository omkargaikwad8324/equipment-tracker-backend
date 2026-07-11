package com.shivswarajya.equipmenttracker.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RevenueReportDTO {

    private Double totalRevenue;

    private Double totalPaid;

    private Double totalPending;

    private Long totalInvoices;

    private Double collectionPercentage;
}