package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.EquipmentResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;

@Component
public class EquipmentMapper {

    public EquipmentResponseDTO toResponse(Equipment equipment) {

        if (equipment == null) {
            return null;
        }

        return EquipmentResponseDTO.builder()
                .id(equipment.getId())
                .equipmentCode(equipment.getEquipmentCode())
                .equipmentName(equipment.getEquipmentName())
                .equipmentType(equipment.getEquipmentType())
                .brand(equipment.getBrand())
                .model(equipment.getModel())
                .registrationNumber(equipment.getRegistrationNumber())
                .engineNumber(equipment.getEngineNumber())
                .chassisNumber(equipment.getChassisNumber())
                .ownerName(equipment.getOwnerName())
                .purchaseDate(equipment.getPurchaseDate())
                .purchasePrice(equipment.getPurchasePrice())
                .hourlyRate(equipment.getHourlyRate())
                .currentMeterReading(equipment.getCurrentMeterReading())
                .fuelType(equipment.getFuelType())
                .status(equipment.getStatus())
                .insuranceExpiry(equipment.getInsuranceExpiry())
                .fitnessExpiry(equipment.getFitnessExpiry())
                .permitExpiry(equipment.getPermitExpiry())
                .pollutionExpiry(equipment.getPollutionExpiry())
                .remarks(equipment.getRemarks())
                .build();
    }
}