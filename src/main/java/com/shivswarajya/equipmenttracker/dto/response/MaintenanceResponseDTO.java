package com.shivswarajya.equipmenttracker.dto.response;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;
import com.shivswarajya.equipmenttracker.enums.MaintenanceType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MaintenanceResponseDTO {

    private Long id;

    private String maintenanceNo;

    private String equipmentName;

    private MaintenanceType maintenanceType;

    private LocalDate maintenanceDate;

    private Double cost;

    private String vendor;

    private String description;

    private LocalDate nextServiceDate;

    private MaintenanceStatus maintenanceStatus;

}