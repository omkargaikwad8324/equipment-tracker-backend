package com.shivswarajya.equipmenttracker.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.EquipmentRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;

public interface EquipmentService {

    Equipment addEquipment(EquipmentRequestDTO dto);

    Equipment updateEquipment(Long id, EquipmentRequestDTO dto);

    void deleteEquipment(Long id);

    Equipment getEquipment(Long id);

    List<Equipment> getAllEquipment();

    List<Equipment> searchEquipment(String keyword);

}