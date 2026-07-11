package com.shivswarajya.equipmenttracker.dto.response;

import com.shivswarajya.equipmenttracker.enums.DriverStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class DriverResponseDTO {

    private Long id;

    private String driverCode;

    private String fullName;

    private String mobile;

    private String alternateMobile;

    private String address;

    private String aadhaarNumber;

    private String licenseNumber;

    private LocalDate licenseExpiry;

    private LocalDate joiningDate;

    private BigDecimal salary;

    private DriverStatus status;

    private String remarks;
}