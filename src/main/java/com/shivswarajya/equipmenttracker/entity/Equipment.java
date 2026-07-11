package com.shivswarajya.equipmenttracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import com.shivswarajya.equipmenttracker.enums.EquipmentType;

@Entity
@Table(name = "equipment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String equipmentCode;

    @NotBlank(message = "Equipment name is required")
    @Column(nullable = false)
    private String equipmentName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;

    @NotBlank
    private String brand;

    private String model;

    @Column(unique = true)
    private String registrationNumber;

    @Column(unique = true)
    private String engineNumber;

    @Column(unique = true)
    private String chassisNumber;

    private String ownerName;

    private LocalDate purchaseDate;

    @DecimalMin(value = "0.0")
    private BigDecimal purchasePrice;

    @DecimalMin(value = "0.0")
    private BigDecimal hourlyRate;

    @DecimalMin(value = "0.0")
    private Double currentMeterReading;

    private String fuelType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EquipmentStatus status = EquipmentStatus.WORKING;

    private LocalDate insuranceExpiry;

    private LocalDate fitnessExpiry;

    private LocalDate permitExpiry;

    private LocalDate pollutionExpiry;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}