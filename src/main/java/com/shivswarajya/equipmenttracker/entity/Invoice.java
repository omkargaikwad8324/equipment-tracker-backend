package com.shivswarajya.equipmenttracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.shivswarajya.equipmenttracker.enums.PaymentStatus;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String invoiceNumber;

    @Column(nullable = false)
    private LocalDate invoiceDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false, unique = true)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Double subtotal;

    @Column(nullable = false)
    private Double gstAmount;

    @Column(nullable = false)
    private Double grandTotal;

    @Builder.Default
    private Double paidAmount = 0.0;

    @Builder.Default
    private Double balanceAmount = 0.0;

    @Column(length = 500)
    private String remarks;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
}