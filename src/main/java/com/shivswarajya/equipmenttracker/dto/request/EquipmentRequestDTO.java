package com.shivswarajya.equipmenttracker.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.EquipmentType;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
@Data
public class EquipmentRequestDTO {

    @NotBlank(message = "Equipment code is required")
    private String equipmentCode;

    @NotBlank(message = "Equipment name is required")
    private String equipmentName;

    private EquipmentType equipmentType;

    @NotBlank(message = "Brand is required")
    private String brand;

    private String model;

    private String registrationNumber;

    private String engineNumber;

    private String chassisNumber;

    private String ownerName;

    private LocalDate purchaseDate;

    @DecimalMin("0.0")
    private BigDecimal purchasePrice;

    @DecimalMin("0.0")
    private BigDecimal hourlyRate;

    @DecimalMin("0.0")
    private Double currentMeterReading;

    private String fuelType;

    private EquipmentStatus status;

    private LocalDate insuranceExpiry;

    private LocalDate fitnessExpiry;

    private LocalDate permitExpiry;

    private LocalDate pollutionExpiry;

    private String remarks;
}