package com.shivswarajya.equipmenttracker.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FuelRequestDTO {

    @NotNull
    private Long workOrderId;

    @NotNull
    private Long equipmentId;

    @Positive
    private Double quantity;

    @Positive
    private Double ratePerLitre;

    private String fuelStation;

    private String remarks;
}