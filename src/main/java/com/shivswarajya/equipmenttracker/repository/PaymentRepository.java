package com.shivswarajya.equipmenttracker.repository;

import com.shivswarajya.equipmenttracker.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findTopByOrderByIdDesc();

    List<Payment> findByInvoiceId(Long invoiceId);

    Optional<Payment> findByPaymentNumber(String paymentNumber);

    List<Payment> findByPaymentDate(LocalDate paymentDate);

    @Query("""
            SELECT p
            FROM Payment p
            JOIN FETCH p.invoice i
            JOIN FETCH i.customer
            WHERE p.id = :id
            """)
    Optional<Payment> findByIdWithDetails(
            @Param("id") Long id);

    @Query("""
            SELECT p
            FROM Payment p
            JOIN FETCH p.invoice i
            JOIN FETCH i.customer
            """)
    List<Payment> findAllWithDetails();

    @Query("""
            SELECT p
            FROM Payment p
            JOIN FETCH p.invoice i
            JOIN FETCH i.customer
            WHERE i.id = :invoiceId
            """)
    List<Payment> findByInvoiceIdWithDetails(
            @Param("invoiceId") Long invoiceId);

}
