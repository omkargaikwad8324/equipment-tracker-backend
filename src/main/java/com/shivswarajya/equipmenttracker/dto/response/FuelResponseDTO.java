package com.shivswarajya.equipmenttracker.dto.response;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuelResponseDTO {

    private Long id;

    private String fuelEntryNo;

    private String workOrderNo;

    private String equipmentName;

    private LocalDate fuelDate;

    private Double quantity;

    private Double ratePerLitre;

    private Double totalAmount;

    private String fuelStation;

    private String remarks;
}