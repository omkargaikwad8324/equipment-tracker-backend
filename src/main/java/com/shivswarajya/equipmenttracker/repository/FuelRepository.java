package com.shivswarajya.equipmenttracker.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shivswarajya.equipmenttracker.entity.Fuel;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

public interface FuelRepository extends JpaRepository<Fuel, Long> {

        boolean existsByWorkOrderId(Long workOrderId);
        
        Optional<Fuel> findTopByOrderByIdDesc();

        Optional<Fuel> findByFuelEntryNo(String fuelEntryNo);

        List<Fuel> findByWorkOrderId(Long workOrderId);

        List<Fuel> findByEquipmentId(Long equipmentId);

        List<Fuel> findByFuelDate(LocalDate fuelDate);

        @Query("""
                        SELECT f
                        FROM Fuel f
                        JOIN FETCH f.workOrder
                        JOIN FETCH f.equipment
                        """)
        List<Fuel> findAllWithDetails();

        @Query("""
                        SELECT f
                        FROM Fuel f
                        JOIN FETCH f.workOrder
                        JOIN FETCH f.equipment
                        WHERE f.id = :id
                        """)
        Optional<Fuel> findByIdWithDetails(
                        @Param("id") Long id);

        @Query("""
                        SELECT f
                        FROM Fuel f
                        JOIN FETCH f.workOrder
                        JOIN FETCH f.equipment
                        WHERE f.equipment.id = :equipmentId
                        """)
        List<Fuel> findByEquipmentIdWithDetails(
                        @Param("equipmentId") Long equipmentId);

        @Query("""
                        SELECT f
                        FROM Fuel f
                        JOIN FETCH f.workOrder
                        JOIN FETCH f.equipment
                        WHERE f.workOrder.id = :workOrderId
                        """)
        List<Fuel> findByWorkOrderIdWithDetails(
                        @Param("workOrderId") Long workOrderId);

        @Query("""
                        SELECT f
                        FROM Fuel f
                        JOIN FETCH f.workOrder
                        JOIN FETCH f.equipment
                        WHERE f.fuelDate = :fuelDate
                        """)
        List<Fuel> findByFuelDateWithDetails(
                        @Param("fuelDate") LocalDate fuelDate);

        @Query("""
                        SELECT COALESCE(SUM(f.totalAmount), 0)
                        FROM Fuel f
                        """)
        Double getTotalFuelExpense();
}