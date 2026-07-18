package com.shivswarajya.equipmenttracker.dto.response;

import java.math.BigDecimal;

import com.shivswarajya.equipmenttracker.enums.EquipmentType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkOrderItemResponseDTO {

    private Long id;

    private EquipmentType equipmentType;

    private String equipmentName;

    private String driverName;

    private Integer quantity;

    private Double totalHours;

    private BigDecimal hourlyRate;

    private Integer trips;

    private BigDecimal tripRate;

    private Double startMeter;

    private Double endMeter;

    private Double dieselUsed;

    private BigDecimal hoursAmount;

    private BigDecimal tripAmount;

    private BigDecimal totalAmount;

    private String remarks;

    private Long equipmentId;

    private Long driverId;

}