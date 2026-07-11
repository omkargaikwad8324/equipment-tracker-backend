package com.shivswarajya.equipmenttracker.dashboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryDTO {

    // Revenue
    private Double todayRevenue;
    private Double monthlyRevenue;
    private Double totalRevenue;

    // Payments
    private Double pendingAmount;
    private Double receivedAmount;
    private Long totalPayments;
    // Working Hours
    private Double todayHours;
    private Double monthlyHours;

    // Masters
    private Long totalCustomers;
    private Long totalEquipment;
    private Long totalDrivers;

    private Long workingEquipment;

    private Long idleEquipment;

    private Long maintenanceEquipment;

    private Long breakdownEquipment;

    // Work Orders
    private Long totalWorkOrders;
    private Long completedWorkOrders;
    private Long pendingWorkOrders;
    private Long inProgressWorkOrders;

    // Invoice
    private Long totalInvoices;

    // Future Modules
    private Double fuelExpense;
    private Double maintenanceExpense;
    private Double miscellaneousExpense;
    private Double totalExpenses;
    private Double completionPercentage;
    private Double netProfit;
}