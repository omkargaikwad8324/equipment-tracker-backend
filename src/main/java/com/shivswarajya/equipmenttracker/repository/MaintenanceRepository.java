package com.shivswarajya.equipmenttracker.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;

public interface MaintenanceRepository extends JpaRepository<Maintenance, Long> {

        Optional<Maintenance> findTopByOrderByIdDesc();

        List<Maintenance> findByEquipmentId(Long equipmentId);

        List<Maintenance> findByMaintenanceStatus(
                        MaintenanceStatus status);

        Optional<Maintenance> findByMaintenanceNo(
                        String maintenanceNo);

        @Query("""
                        SELECT m
                        FROM Maintenance m
                        JOIN FETCH m.equipment
                        """)
        List<Maintenance> findAllWithDetails();

        @Query("""
                        SELECT m
                        FROM Maintenance m
                        JOIN FETCH m.equipment
                        WHERE m.id = :id
                        """)
        Optional<Maintenance> findByIdWithDetails(
                        @Param("id") Long id);

        @Query("""
                        SELECT m
                        FROM Maintenance m
                        JOIN FETCH m.equipment
                        WHERE m.equipment.id = :equipmentId
                        """)
        List<Maintenance> findByEquipmentIdWithDetails(
                        @Param("equipmentId") Long equipmentId);

        @Query("""
                        SELECT m
                        FROM Maintenance m
                        JOIN FETCH m.equipment
                        WHERE m.maintenanceStatus = :status
                        """)
        List<Maintenance> findByStatusWithDetails(
                        @Param("status") MaintenanceStatus status);

        @Query("""
                        SELECT COALESCE(SUM(m.cost), 0)
                        FROM Maintenance m
                        """)
        Double getTotalMaintenanceExpense();
}