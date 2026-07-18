package com.shivswarajya.equipmenttracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.enums.WorkStatus;

@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

        long countByStatus(WorkStatus status);

        // ===========================
        // Basic Lookup
        // ===========================

        Optional<WorkOrder> findByWorkOrderNo(String workOrderNo);

        // ===========================
        // Customer
        // ===========================

        List<WorkOrder> findByCustomerId(Long customerId);

        List<WorkOrder> findByCustomerIdOrderByWorkDateDesc(Long customerId);

        List<WorkOrder> findByCustomer_NameContainingIgnoreCaseOrderByWorkDateDesc(String name);

        // ===========================
        // Status
        // ===========================

        List<WorkOrder> findByStatus(WorkStatus status);

        // ===========================
        // Date Filters
        // ===========================

        List<WorkOrder> findByWorkDate(LocalDate workDate);

        List<WorkOrder> findByWorkDateBetween(
                        LocalDate startDate,
                        LocalDate endDate);

        // ===========================
        // Site
        // ===========================

        List<WorkOrder> findBySiteNameContainingIgnoreCase(String siteName);

        // ===========================
        // Fetch Items
        // ===========================

        @EntityGraph(attributePaths = {
                        "customer",
                        "items",
                        "items.equipment",
                        "items.driver"
        })
        Optional<WorkOrder> findWithItemsById(Long id);

}