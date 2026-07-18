package com.shivswarajya.equipmenttracker.entity;

import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany(mappedBy = "workOrder", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<WorkOrderItem> items = new ArrayList<>();

    // Customer assigned to this work
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private LocalDate workDate;

    @Column(nullable = false)
    private String siteName;

    @Column(length = 1000)
    private String workDescription;

    private BigDecimal totalAmount;

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

    public void addItem(WorkOrderItem item) {

        items.add(item);
        item.setWorkOrder(this);

    }

    public void removeItem(WorkOrderItem item) {

        items.remove(item);
        item.setWorkOrder(null);

    }

    
}
