package com.shivswarajya.equipmenttracker.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Service;

import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;
import com.shivswarajya.equipmenttracker.service.WorkOrderCalculationService;

@Service
public class WorkOrderCalculationServiceImpl implements WorkOrderCalculationService {

    private static final BigDecimal ZERO = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);

    @Override
    public BigDecimal calculateHoursAmount(WorkOrderItem item) {

        if (item == null
                || item.getQuantity() == null
                || item.getTotalHours() == null
                || item.getHourlyRate() == null) {

            return ZERO;
        }

        return BigDecimal.valueOf(item.getQuantity())
                .multiply(BigDecimal.valueOf(item.getTotalHours()))
                .multiply(item.getHourlyRate())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateTripsAmount(WorkOrderItem item) {

        if (item == null
                || item.getQuantity() == null
                || item.getTrips() == null
                || item.getTripRate() == null) {

            return ZERO;
        }

        return BigDecimal.valueOf(item.getQuantity())
                .multiply(BigDecimal.valueOf(item.getTrips()))
                .multiply(item.getTripRate())
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateItemTotal(WorkOrderItem item) {

        BigDecimal hoursAmount = calculateHoursAmount(item);
        BigDecimal tripsAmount = calculateTripsAmount(item);

        return hoursAmount
                .add(tripsAmount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculateGrandTotal(WorkOrder workOrder) {

        if (workOrder == null || workOrder.getItems() == null) {
            return ZERO;
        }

        BigDecimal grandTotal = ZERO;

        for (WorkOrderItem item : workOrder.getItems()) {

            BigDecimal itemTotal = calculateItemTotal(item);

            grandTotal = grandTotal.add(itemTotal);
        }

        return grandTotal.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void calculateWorkOrder(WorkOrder workOrder) {

        if (workOrder == null || workOrder.getItems() == null) {
            return;
        }

        BigDecimal grandTotal = ZERO;

        for (WorkOrderItem item : workOrder.getItems()) {

            BigDecimal hoursAmount = calculateHoursAmount(item);
            BigDecimal tripsAmount = calculateTripsAmount(item);
            BigDecimal itemTotal = hoursAmount.add(tripsAmount);

            item.setHoursAmount(hoursAmount);
            item.setTripAmount(tripsAmount);
            item.setTotalAmount(itemTotal);

            grandTotal = grandTotal.add(itemTotal);
        }

        workOrder.setTotalAmount(grandTotal.setScale(2, RoundingMode.HALF_UP));
    }
}