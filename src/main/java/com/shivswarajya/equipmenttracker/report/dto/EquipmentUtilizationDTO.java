package com.shivswarajya.equipmenttracker.report.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EquipmentUtilizationDTO {

    private Long equipmentId;

    private String equipmentName;

    private String registrationNumber;

    private Double totalWorkingHours;

    private Double totalRevenue;

    private Double fuelExpense;

    private Double maintenanceExpense;

    private Double netProfit;

    private Double profitPerHour;
}