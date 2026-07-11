package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceRepository
                extends JpaRepository<Invoice, Long> {

        Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

        Optional<Invoice> findTopByOrderByIdDesc();

        boolean existsByWorkOrderId(Long workOrderId);

        List<Invoice> findByInvoiceDate(LocalDate invoiceDate);

        List<Invoice> findByCustomerId(Long customerId);

        List<Invoice> findByCustomer_NameContainingIgnoreCase(String customerName);

        @Query("""
                        SELECT i
                        FROM Invoice i
                        JOIN FETCH i.customer
                        JOIN FETCH i.workOrder w
                        JOIN FETCH w.equipment
                        """)
        List<Invoice> findAllWithDetails();

        @Query("""
                        SELECT i
                        FROM Invoice i
                        JOIN FETCH i.customer
                        JOIN FETCH i.workOrder w
                        JOIN FETCH w.equipment
                        WHERE i.id = :id
                        """)
        Optional<Invoice> findByIdWithDetails(@Param("id") Long id);

        @Query("""
                        SELECT COALESCE(SUM(i.grandTotal), 0)
                        FROM Invoice i
                        """)
        Double getTotalRevenue();

        @Query("""
                        SELECT COALESCE(SUM(i.paidAmount), 0)
                        FROM Invoice i
                        """)
        Double getTotalPaidAmount();

        @Query("""
                        SELECT COALESCE(SUM(i.balanceAmount), 0)
                        FROM Invoice i
                        """)
        Double getTotalPendingAmount();

        @Query("""
                        SELECT COALESCE(SUM(i.grandTotal), 0)
                        FROM Invoice i
                        WHERE i.invoiceDate = CURRENT_DATE
                        """)
        Double getTodayRevenue();

        @Query("""
                        SELECT COALESCE(SUM(i.grandTotal), 0)
                        FROM Invoice i
                        WHERE YEAR(i.invoiceDate) = YEAR(CURRENT_DATE)
                        AND MONTH(i.invoiceDate) = MONTH(CURRENT_DATE)
                        """)
        Double getCurrentMonthRevenue();

        @Query("""
                        SELECT COUNT(i)
                        FROM Invoice i
                        """)
        Long getTotalInvoices();

}