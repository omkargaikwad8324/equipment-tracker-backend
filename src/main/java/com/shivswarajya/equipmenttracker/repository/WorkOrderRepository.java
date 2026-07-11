package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {

        Optional<WorkOrder> findTopByOrderByIdDesc();

        Optional<WorkOrder> findByWorkOrderNo(String workOrderNo);

        List<WorkOrder> findByWorkDate(LocalDate workDate);

        List<WorkOrder> findByStatus(WorkStatus status);

        List<WorkOrder> findByCustomer_NameContainingIgnoreCase(String customerName);

        List<WorkOrder> findByCustomerId(Long customerId);

        List<WorkOrder> findByEquipmentId(Long equipmentId);

        List<WorkOrder> findByDriverId(Long driverId);

        @Query("""
                        SELECT w
                        FROM WorkOrder w
                        JOIN FETCH w.customer
                        JOIN FETCH w.equipment
                        JOIN FETCH w.driver
                        """)
        List<WorkOrder> findAllWithDetails();

        @Query("""
                        SELECT w
                        FROM WorkOrder w
                        JOIN FETCH w.customer
                        JOIN FETCH w.equipment
                        JOIN FETCH w.driver
                        WHERE w.id = :id
                        """)
        Optional<WorkOrder> findByIdWithDetails(@Param("id") Long id);

        boolean existsByEquipmentIdAndStatusIn(
                        Long equipmentId,
                        List<WorkStatus> statuses);

        boolean existsByDriverIdAndStatusIn(
                        Long driverId,
                        List<WorkStatus> statuses);

        boolean existsByEquipmentIdAndStatusInAndIdNot(
                        Long equipmentId,
                        List<WorkStatus> statuses,
                        Long id);

        boolean existsByDriverIdAndStatusInAndIdNot(
                        Long driverId,
                        List<WorkStatus> statuses,
                        Long id);

        @Query("""
                        SELECT COALESCE(SUM(w.totalHours), 0)
                        FROM WorkOrder w
                        """)
        Double getTotalWorkingHours();

        @Query("""
                        SELECT COALESCE(SUM(w.totalHours), 0)
                        FROM WorkOrder w
                        WHERE w.workDate = CURRENT_DATE
                        """)
        Double getTodayWorkingHours();

        @Query("""
                        SELECT COALESCE(SUM(w.totalHours), 0)
                        FROM WorkOrder w
                        WHERE YEAR(w.workDate) = YEAR(CURRENT_DATE)
                        AND MONTH(w.workDate) = MONTH(CURRENT_DATE)
                        """)
        Double getCurrentMonthWorkingHours();

        @Query("""
                        SELECT COALESCE(SUM(w.totalAmount), 0)
                        FROM WorkOrder w
                        """)
        Double getTotalWorkOrderRevenue();

        @Query("""
                        SELECT COUNT(w)
                        FROM WorkOrder w
                        WHERE w.status = 'COMPLETED'
                        """)
        Long countCompletedWorkOrders();

        @Query("""
                        SELECT COUNT(w)
                        FROM WorkOrder w
                        WHERE w.status = 'PENDING'
                        """)
        Long countPendingWorkOrders();

        @Query("""
                        SELECT COUNT(w)
                        FROM WorkOrder w
                        WHERE w.status = 'IN_PROGRESS'
                        """)
        Long countInProgressWorkOrders();

}