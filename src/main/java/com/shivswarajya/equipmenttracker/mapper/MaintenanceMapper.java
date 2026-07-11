package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.MaintenanceResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Maintenance;

@Component
public class MaintenanceMapper {

    public MaintenanceResponseDTO toResponse(Maintenance maintenance) {

        return MaintenanceResponseDTO.builder()
                .id(maintenance.getId())
                .maintenanceNo(maintenance.getMaintenanceNo())
                .maintenanceStatus(
                        maintenance.getMaintenanceStatus())
                .equipmentName(maintenance.getEquipment().getEquipmentName())
                .maintenanceType(maintenance.getMaintenanceType())
                .maintenanceDate(maintenance.getMaintenanceDate())
                .cost(maintenance.getCost())
                .vendor(maintenance.getVendor())
                .description(maintenance.getDescription())
                .nextServiceDate(maintenance.getNextServiceDate())
                .build();

    }

}