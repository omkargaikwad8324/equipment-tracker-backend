package com.shivswarajya.equipmenttracker.dto.request;

import java.math.BigDecimal;

import com.shivswarajya.equipmenttracker.enums.EquipmentType;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import com.shivswarajya.equipmenttracker.repository.WorkOrderItemRepository;
import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;
import java.util.Objects;

@Data
public class WorkOrderItemRequestDTO {

    private EquipmentType equipmentType;
    @NotNull
    private Long equipmentId;

    @NotNull
    private Long driverId;

    @Positive
    private Integer quantity;

    @PositiveOrZero
    private Double totalHours;

    private BigDecimal hourlyRate;

    private Integer trips;

    private BigDecimal tripRate;

    private Double startMeter;

    private Double endMeter;

    private Double dieselUsed;

    private String remarks;
    

}