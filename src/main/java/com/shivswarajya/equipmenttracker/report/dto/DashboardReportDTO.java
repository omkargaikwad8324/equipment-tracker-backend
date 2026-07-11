package com.shivswarajya.equipmenttracker.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardReportDTO {

    private Long totalCustomers;

    private Long totalEquipment;

    private Long totalDrivers;

    private Long totalInvoices;
    
    private Double totalWorkingHours;

    private Double totalRevenue;

    private Double totalExpenses;

    private Double totalFuelExpense;

    private Double totalMaintenanceExpense;

    private Double pendingAmount;

    private Double receivedAmount;

    private Double netProfit;

    private Double profitPercentage;

}