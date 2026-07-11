package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.FuelResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Fuel;

@Component
public class FuelMapper {

    public FuelResponseDTO toResponse(Fuel fuel) {

        if (fuel == null) {
            return null;
        }

        return FuelResponseDTO.builder()
                .id(fuel.getId())
                .fuelEntryNo(fuel.getFuelEntryNo())
                .workOrderNo(fuel.getWorkOrder().getWorkOrderNo())
                .equipmentName(fuel.getEquipment().getEquipmentName())
                .fuelDate(fuel.getFuelDate())
                .quantity(fuel.getQuantity())
                .ratePerLitre(fuel.getRatePerLitre())
                .totalAmount(fuel.getTotalAmount())
                .fuelStation(fuel.getFuelStation())
                .remarks(fuel.getRemarks())
                .build();
    }
}