package com.shivswarajya.equipmenttracker.mapper;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;

@Component
public class WorkOrderMapper {

    public WorkOrderResponseDTO toResponse(WorkOrder workOrder) {

        if (workOrder == null) {
            return null;
        }

        return WorkOrderResponseDTO.builder()
                .id(workOrder.getId())
                .workOrderNo(workOrder.getWorkOrderNo())
                .customerName(workOrder.getCustomer().getName())
                .equipmentName(workOrder.getEquipment().getEquipmentName())
                .driverName(workOrder.getDriver().getFullName())
                .workDate(workOrder.getWorkDate())
                .siteName(workOrder.getSiteName())
                .workDescription(workOrder.getWorkDescription())
                .startMeter(workOrder.getStartMeter())
                .endMeter(workOrder.getEndMeter())
                .totalHours(workOrder.getTotalHours())
                .hourlyRate(workOrder.getHourlyRate())
                .totalAmount(workOrder.getTotalAmount())
                .dieselUsed(workOrder.getDieselUsed())
                .status(workOrder.getStatus())
                .remarks(workOrder.getRemarks())
                .build();
    }
}