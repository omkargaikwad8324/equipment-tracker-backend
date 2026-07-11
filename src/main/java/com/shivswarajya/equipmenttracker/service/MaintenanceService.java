package com.shivswarajya.equipmenttracker.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.MaintenanceRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;

public interface MaintenanceService {

    Maintenance addMaintenance(MaintenanceRequestDTO dto);

    Maintenance getMaintenance(Long id);

    List<Maintenance> getAllMaintenance();

    List<Maintenance> getByEquipment(Long equipmentId);

    List<Maintenance> getByStatus(
            MaintenanceStatus status);

    void deleteMaintenance(Long id);

    Maintenance getByMaintenanceNo(
        String maintenanceNo);

}