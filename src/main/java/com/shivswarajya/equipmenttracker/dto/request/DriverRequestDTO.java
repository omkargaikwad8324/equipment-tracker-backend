package com.shivswarajya.equipmenttracker.dto.request;

import com.shivswarajya.equipmenttracker.enums.DriverStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DriverRequestDTO {

    @NotBlank(message = "Driver code is required")
    private String driverCode;

    @NotBlank(message = "Driver name is required")
    private String fullName;

    @NotBlank(message = "Mobile number is required")
    private String mobile;

    private String alternateMobile;

    private String address;

    private String aadhaarNumber;

    @NotBlank(message = "License number is required")
    private String licenseNumber;

    private LocalDate licenseExpiry;

    private LocalDate joiningDate;

    private BigDecimal salary;

    private DriverStatus status;

    private String remarks;
}