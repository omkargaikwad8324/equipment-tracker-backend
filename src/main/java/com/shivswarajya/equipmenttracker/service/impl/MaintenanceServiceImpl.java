package com.shivswarajya.equipmenttracker.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shivswarajya.equipmenttracker.dto.request.MaintenanceRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.MaintenanceRepository;
import com.shivswarajya.equipmenttracker.service.MaintenanceService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public Maintenance addMaintenance(MaintenanceRequestDTO dto) {

        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));

        if (dto.getCost() < 0) {
            throw new BadRequestException(
                    "Maintenance cost cannot be negative.");
        }
        if (dto.getMaintenanceDate() == null) {
            throw new BadRequestException(
                    "Maintenance date is required.");
        }
        if (dto.getMaintenanceDate().isAfter(LocalDate.now())) {
            throw new BadRequestException(
                    "Maintenance date cannot be in the future.");
        }
        if (dto.getNextServiceDate() != null
                && dto.getNextServiceDate()
                        .isBefore(dto.getMaintenanceDate())) {

            throw new BadRequestException(
                    "Next service date cannot be before maintenance date.");
        }
        Maintenance maintenance = Maintenance.builder()
                .maintenanceNo(generateMaintenanceNumber())
                .equipment(equipment)
                .maintenanceType(dto.getMaintenanceType())
                .maintenanceStatus(
                        dto.getMaintenanceStatus() == null
                                ? MaintenanceStatus.SCHEDULED
                                : dto.getMaintenanceStatus())
                .maintenanceDate(dto.getMaintenanceDate())
                .cost(dto.getCost())
                .vendor(dto.getVendor())
                .description(dto.getDescription())
                .nextServiceDate(dto.getNextServiceDate())
                .build();

        Maintenance saved = maintenanceRepository.save(maintenance);

        updateEquipmentStatus(
                equipment,
                saved.getMaintenanceStatus());

        equipmentRepository.save(equipment);

        return saved;
    }

    @Override
    public Maintenance getMaintenance(Long id) {

        return maintenanceRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance record not found"));
    }

    @Override
    public Maintenance getByMaintenanceNo(
            String maintenanceNo) {

        return maintenanceRepository
                .findByMaintenanceNo(maintenanceNo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Maintenance record not found"));
    }

    @Override
    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAllWithDetails();
    }

    @Override
    public List<Maintenance> getByEquipment(Long equipmentId) {
        return maintenanceRepository.findByEquipmentIdWithDetails(equipmentId);
    }

    @Override
    public List<Maintenance> getByStatus(
            MaintenanceStatus status) {

        return maintenanceRepository
                .findByStatusWithDetails(status);
    }

    @Override
    public void deleteMaintenance(Long id) {
        maintenanceRepository.delete(getMaintenance(id));
    }

    private String generateMaintenanceNumber() {

        Optional<Maintenance> last = maintenanceRepository.findTopByOrderByIdDesc();

        if (last.isEmpty()) {
            return "MAIN-000001";
        }

        String lastNo = last.get().getMaintenanceNo();

        int number = Integer.parseInt(lastNo.substring(5));

        return String.format("MAIN-%06d", number + 1);
    }

    private void updateEquipmentStatus(
            Equipment equipment,
            MaintenanceStatus maintenanceStatus) {

        switch (maintenanceStatus) {

            case SCHEDULED:
            case IN_PROGRESS:
                equipment.setStatus(
                        EquipmentStatus.UNDER_MAINTENANCE);
                break;

            case COMPLETED:
                equipment.setStatus(
                        EquipmentStatus.WORKING);
                break;

            case CANCELLED:
                equipment.setStatus(
                        EquipmentStatus.IDLE);
                break;
        }
    }
}