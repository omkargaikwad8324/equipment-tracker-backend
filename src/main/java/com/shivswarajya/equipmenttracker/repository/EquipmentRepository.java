package com.shivswarajya.equipmenttracker.repository;

import org.springframework.data.jpa.repository.Query;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {

    Optional<Equipment> findByEquipmentCode(String equipmentCode);

    Optional<Equipment> findByRegistrationNumber(String registrationNumber);

    List<Equipment> findByEquipmentNameContainingIgnoreCase(String equipmentName);

    List<Equipment> findByStatus(EquipmentStatus status);

    long countByStatus(EquipmentStatus status);
}