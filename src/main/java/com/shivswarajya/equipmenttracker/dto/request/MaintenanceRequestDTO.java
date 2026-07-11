package com.shivswarajya.equipmenttracker.dto.request;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;
import com.shivswarajya.equipmenttracker.enums.MaintenanceType;

import lombok.Data;

@Data
public class MaintenanceRequestDTO {

    private Long equipmentId;

    private MaintenanceType maintenanceType;

    private MaintenanceStatus maintenanceStatus;

    private LocalDate maintenanceDate;

    private Double cost;

    private String vendor;

    private String description;

    private LocalDate nextServiceDate;

}