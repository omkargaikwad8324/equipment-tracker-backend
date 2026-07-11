package com.shivswarajya.equipmenttracker.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExpenseReportDTO {

    private Double fuelExpense;

    private Double maintenanceExpense;

    private Double miscellaneousExpense;

    private Double totalExpense;

    private String highestExpenseType;
}