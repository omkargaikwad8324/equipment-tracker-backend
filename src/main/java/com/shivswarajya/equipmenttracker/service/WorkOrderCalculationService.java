package com.shivswarajya.equipmenttracker.service;

import java.math.BigDecimal;

import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;

public interface WorkOrderCalculationService {

    /**
     * Calculates hours amount for a single work item.
     */
    BigDecimal calculateHoursAmount(WorkOrderItem item);

    /**
     * Calculates trip amount for a single work item.
     */
    BigDecimal calculateTripsAmount(WorkOrderItem item);

    /**
     * Calculates total amount of a single work item.
     */
    BigDecimal calculateItemTotal(WorkOrderItem item);

    /**
     * Calculates all item amounts and updates the grand total
     * on the WorkOrder.
     */
    BigDecimal calculateGrandTotal(WorkOrder workOrder);

    /**
     * Calculates the complete work order.
     * Updates:
     * - hoursAmount
     * - tripAmount
     * - totalAmount (item)
     * - grand total (work order)
     */
    void calculateWorkOrder(WorkOrder workOrder);

}