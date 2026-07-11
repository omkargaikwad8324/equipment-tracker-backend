package com.shivswarajya.equipmenttracker.entity;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String workOrderNo;

    @OneToOne(mappedBy = "workOrder", cascade = CascadeType.ALL)
    private Invoice invoice;
    
    // Customer assigned to this work
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    // Equipment used
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    // Driver assigned
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @Column(nullable = false)
    private LocalDate workDate;

    @Column(nullable = false)
    private String siteName;

    @Column(length = 1000)
    private String workDescription;

    @Column(nullable = false)
    private Double startMeter;

    @Column(nullable = false)
    private Double endMeter;

    private Double totalHours;

    private BigDecimal hourlyRate;

    private BigDecimal totalAmount;

    private Double dieselUsed;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private WorkStatus status = WorkStatus.PENDING;

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
