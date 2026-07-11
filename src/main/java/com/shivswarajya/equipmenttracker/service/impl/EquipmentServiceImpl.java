package com.shivswarajya.equipmenttracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shivswarajya.equipmenttracker.dto.request.EquipmentRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.service.EquipmentService;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;

@Service
public class EquipmentServiceImpl implements EquipmentService {

    private final EquipmentRepository equipmentRepository;

    public EquipmentServiceImpl(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    public Equipment addEquipment(EquipmentRequestDTO dto) {

        if (equipmentRepository.findByEquipmentCode(dto.getEquipmentCode()).isPresent()) {
            throw new BadRequestException(
                    "Equipment Code already exists.");
        }

        if (dto.getRegistrationNumber() != null
                && equipmentRepository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {

            throw new BadRequestException(
                    "Registration Number already exists.");
        }
        Equipment equipment = Equipment.builder()
                .equipmentCode(dto.getEquipmentCode())
                .equipmentName(dto.getEquipmentName())
                .equipmentType(dto.getEquipmentType())
                .brand(dto.getBrand())
                .model(dto.getModel())
                .registrationNumber(dto.getRegistrationNumber())
                .engineNumber(dto.getEngineNumber())
                .chassisNumber(dto.getChassisNumber())
                .ownerName(dto.getOwnerName())
                .purchaseDate(dto.getPurchaseDate())
                .purchasePrice(dto.getPurchasePrice())
                .hourlyRate(dto.getHourlyRate())
                .currentMeterReading(dto.getCurrentMeterReading())
                .fuelType(dto.getFuelType())
                .status(dto.getStatus())
                .insuranceExpiry(dto.getInsuranceExpiry())
                .fitnessExpiry(dto.getFitnessExpiry())
                .permitExpiry(dto.getPermitExpiry())
                .pollutionExpiry(dto.getPollutionExpiry())
                .remarks(dto.getRemarks())
                .build();

        return equipmentRepository.save(equipment);
    }

    @Override
    public List<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    @Override
    public Equipment getEquipment(Long id) {

        return equipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Equipment not found with id : " + id));
    }

    @Override
    public Equipment updateEquipment(Long id, EquipmentRequestDTO dto) {

        Equipment equipment = getEquipment(id);
        if (!equipment.getEquipmentCode().equals(dto.getEquipmentCode())
                && equipmentRepository.findByEquipmentCode(dto.getEquipmentCode()).isPresent()) {

            throw new BadRequestException(
                    "Equipment Code already exists.");
        }

        if (dto.getRegistrationNumber() != null
                && !dto.getRegistrationNumber().equals(equipment.getRegistrationNumber())
                && equipmentRepository.findByRegistrationNumber(dto.getRegistrationNumber()).isPresent()) {

            throw new BadRequestException(
                    "Registration Number already exists.");
        }
        equipment.setEquipmentCode(dto.getEquipmentCode());
        equipment.setEquipmentName(dto.getEquipmentName());
        equipment.setEquipmentType(dto.getEquipmentType());
        equipment.setBrand(dto.getBrand());
        equipment.setModel(dto.getModel());
        equipment.setRegistrationNumber(dto.getRegistrationNumber());
        equipment.setEngineNumber(dto.getEngineNumber());
        equipment.setChassisNumber(dto.getChassisNumber());
        equipment.setOwnerName(dto.getOwnerName());
        equipment.setPurchaseDate(dto.getPurchaseDate());
        equipment.setPurchasePrice(dto.getPurchasePrice());
        equipment.setHourlyRate(dto.getHourlyRate());
        equipment.setCurrentMeterReading(dto.getCurrentMeterReading());
        equipment.setFuelType(dto.getFuelType());
        equipment.setStatus(dto.getStatus());
        equipment.setInsuranceExpiry(dto.getInsuranceExpiry());
        equipment.setFitnessExpiry(dto.getFitnessExpiry());
        equipment.setPermitExpiry(dto.getPermitExpiry());
        equipment.setPollutionExpiry(dto.getPollutionExpiry());
        equipment.setRemarks(dto.getRemarks());
        return equipmentRepository.save(equipment);
    }

    @Override
    public void deleteEquipment(Long id) {

        Equipment equipment = getEquipment(id);

        equipmentRepository.delete(equipment);
    }

    @Override
    public List<Equipment> searchEquipment(String keyword) {
        return equipmentRepository.findByEquipmentNameContainingIgnoreCase(keyword);
    }
}