package com.shivswarajya.equipmenttracker.entity;

import com.shivswarajya.equipmenttracker.enums.DriverStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String driverCode;

    @NotBlank
    @Column(nullable = false)
    private String fullName;

    @NotBlank
    @Column(nullable = false, unique = true)
    private String mobile;

    private String alternateMobile;

    private String address;

    @Column(unique = true)
    private String aadhaarNumber;

    @Column(nullable = false, unique = true)
    private String licenseNumber;

    private LocalDate licenseExpiry;

    private LocalDate joiningDate;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private DriverStatus status = DriverStatus.ACTIVE;

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