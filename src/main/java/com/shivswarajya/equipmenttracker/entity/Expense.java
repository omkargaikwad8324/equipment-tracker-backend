package com.shivswarajya.equipmenttracker.entity;

import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String expenseNo;

    @Column(nullable = false)
    private LocalDate expenseDate;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseCategory category;

    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMode paymentMode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column(length = 1000)
    private String remarks;
}
