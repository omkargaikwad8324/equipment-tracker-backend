package com.shivswarajya.equipmenttracker.dto.response;

import com.shivswarajya.equipmenttracker.enums.EquipmentType;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class EquipmentResponseDTO {

    private Long id;

    private String equipmentCode;

    private String equipmentName;

    private EquipmentType equipmentType;

    private String brand;

    private String model;

    private String registrationNumber;

    private String engineNumber;

    private String chassisNumber;

    private String ownerName;

    private LocalDate purchaseDate;

    private BigDecimal purchasePrice;

    private BigDecimal hourlyRate;

    private Double currentMeterReading;

    private String fuelType;

    private EquipmentStatus status;

    private LocalDate insuranceExpiry;

    private LocalDate fitnessExpiry;

    private LocalDate permitExpiry;

    private LocalDate pollutionExpiry;

    private String remarks;
}