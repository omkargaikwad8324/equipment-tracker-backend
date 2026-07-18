package com.shivswarajya.equipmenttracker.entity;

import java.math.BigDecimal;

import com.shivswarajya.equipmenttracker.enums.EquipmentType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "work_order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkOrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Parent Work Order
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    /*
     * Equipment Type
     * (JCB / TRACTOR / DUMPER)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EquipmentType equipmentType;

    /*
     * Specific Equipment
     * Optional
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    /*
     * Driver
     * Optional
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;

    /*
     * Number of Machines
     */
    @Builder.Default
    private Integer quantity = 1;

    /*
     * Hourly Billing
     */
    private Double totalHours;

    private BigDecimal hourlyRate;

    /*
     * Trip Billing
     */
    private Integer trips;

    private BigDecimal tripRate;

    /*
     * Optional Meter Readings
     */
    private Double startMeter;

    private Double endMeter;

    /*
     * Optional Diesel
     */
    private Double dieselUsed;

    /*
     * Calculated Amounts
     */
    @Builder.Default
    private BigDecimal hoursAmount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal tripAmount = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal totalAmount = BigDecimal.ZERO;

    /*
     * Remarks
     */
    @Column(length = 500)
    private String remarks;

    public boolean hasHours() {

        return totalHours != null
                && totalHours > 0;

    }

    public boolean hasTrips() {

        return trips != null
                && trips > 0;

    }

}