package com.shivswarajya.equipmenttracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;
import com.shivswarajya.equipmenttracker.enums.EquipmentType;

@Repository
public interface WorkOrderItemRepository extends JpaRepository<WorkOrderItem, Long> {

    // ===========================
    // Work Order
    // ===========================

    List<WorkOrderItem> findByWorkOrderId(Long workOrderId);

    // ===========================
    // Equipment
    // ===========================

    List<WorkOrderItem> findByEquipmentId(Long equipmentId);

    List<WorkOrderItem> findByEquipmentType(EquipmentType equipmentType);

    // ===========================
    // Driver
    // ===========================

    List<WorkOrderItem> findByDriverId(Long driverId);

    // ===========================
    // Fetch Complete Item
    // ===========================

    @EntityGraph(attributePaths = {
            "equipment",
            "driver",
            "workOrder"
    })
    List<WorkOrderItem> findAllByWorkOrderId(Long workOrderId);

}