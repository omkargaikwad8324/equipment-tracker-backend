package com.shivswarajya.equipmenttracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "fuel_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Fuel extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fuelEntryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Column(nullable = false)
    private LocalDate fuelDate;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double ratePerLitre;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(length = 100)
    private String fuelStation;

    @Column(length = 500)
    private String remarks;
}