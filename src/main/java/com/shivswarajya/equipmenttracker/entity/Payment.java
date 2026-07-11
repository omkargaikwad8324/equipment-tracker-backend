package com.shivswarajya.equipmenttracker.entity;

import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;

    @Column(nullable = false)
    private Double amount;

    @Column(length = 100)
    private String transactionReference;

    @Column(length = 500)
    private String remarks;

}