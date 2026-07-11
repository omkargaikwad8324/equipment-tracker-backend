package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

        List<Expense> findByEquipmentId(Long equipmentId);

        List<Expense> findByExpenseDateBetween(LocalDate from, LocalDate to);

        List<Expense> findByCategory(ExpenseCategory category);

        List<Expense> findByPaymentMode(PaymentMode paymentMode);

        Optional<Expense> findTopByOrderByIdDesc();

        @Query("""
                        SELECT e
                        FROM Expense e
                        LEFT JOIN FETCH e.equipment
                        """)
        List<Expense> findAllWithEquipment();

        @Query("""
                        SELECT e
                        FROM Expense e
                        LEFT JOIN FETCH e.equipment
                        WHERE e.expenseDate BETWEEN :from AND :to
                        """)
        List<Expense> findByExpenseDateBetweenWithEquipment(
                        @Param("from") LocalDate from,
                        @Param("to") LocalDate to);

        @Query("""
                        SELECT e
                        FROM Expense e
                        LEFT JOIN FETCH e.equipment
                        WHERE e.id = :id
                        """)
        Optional<Expense> findByIdWithEquipment(
                        @Param("id") Long id);

        @Query("""
                        SELECT e
                        FROM Expense e
                        LEFT JOIN FETCH e.equipment
                        WHERE e.equipment.id = :equipmentId
                        """)
        List<Expense> findByEquipmentIdWithEquipment(
                        @Param("equipmentId") Long equipmentId);

        @Query("""
                        SELECT e
                        FROM Expense e
                        LEFT JOIN FETCH e.equipment
                        WHERE e.category = :category
                        """)
        List<Expense> findByCategoryWithEquipment(
                        @Param("category") ExpenseCategory category);

        @Query("""
                               SELECT e
                        FROM Expense e
                               LEFT JOIN FETCH e.equipment
                               WHERE e.paymentMode = :paymentMode
                               """)
        List<Expense> findByPaymentModeWithEquipment(
                        @Param("paymentMode") PaymentMode paymentMode);

        @Query("""
                        SELECT COALESCE(SUM(e.amount), 0)
                        FROM Expense e
                        """)
        Double getTotalMiscellaneousExpense();

        @Query("""
                        SELECT COALESCE(SUM(f.totalAmount), 0)
                        FROM Fuel f
                        """)
        Double getTotalFuelExpense();

        @Query("""
                        SELECT COALESCE(SUM(m.cost), 0)
                        FROM Maintenance m
                        """)
        Double getTotalMaintenanceExpense();
}
