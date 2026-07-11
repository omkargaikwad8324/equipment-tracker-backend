package com.shivswarajya.equipmenttracker.entity;

import java.time.LocalDate;

import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;
import com.shivswarajya.equipmenttracker.enums.MaintenanceType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "maintenance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Maintenance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String maintenanceNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    @Enumerated(EnumType.STRING)
    private MaintenanceType maintenanceType;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private MaintenanceStatus maintenanceStatus = MaintenanceStatus.SCHEDULED;

    private LocalDate maintenanceDate;

    

    private Double cost;

    private String vendor;

    private String description;

    private LocalDate nextServiceDate;

}