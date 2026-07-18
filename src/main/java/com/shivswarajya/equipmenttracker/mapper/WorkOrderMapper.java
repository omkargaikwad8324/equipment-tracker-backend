package com.shivswarajya.equipmenttracker.mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderItemRequestDTO;
import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderItemResponseDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;

import io.jsonwebtoken.lang.Collections;

@Component
public class WorkOrderMapper {

    /**
     * Request DTO -> Entity
     */
    public WorkOrder toEntity(WorkOrderRequestDTO dto) {

        if (dto == null) {
            return null;
        }

        WorkOrder workOrder = new WorkOrder();

        workOrder.setWorkDate(dto.getWorkDate());
        workOrder.setSiteName(dto.getSiteName());
        workOrder.setWorkDescription(dto.getWorkDescription());
        workOrder.setRemarks(dto.getRemarks());
        workOrder.setStatus(dto.getStatus());

        List<WorkOrderItem> items = new ArrayList<>();

        if (dto.getItems() != null) {

            for (WorkOrderItemRequestDTO itemDTO : dto.getItems()) {

                WorkOrderItem item = new WorkOrderItem();

                item.setEquipmentType(itemDTO.getEquipmentType());

                item.setQuantity(itemDTO.getQuantity());

                item.setTotalHours(itemDTO.getTotalHours());

                item.setHourlyRate(itemDTO.getHourlyRate());

                item.setTrips(itemDTO.getTrips());

                item.setTripRate(itemDTO.getTripRate());

                item.setStartMeter(itemDTO.getStartMeter());

                item.setEndMeter(itemDTO.getEndMeter());

                item.setDieselUsed(itemDTO.getDieselUsed());

                item.setRemarks(itemDTO.getRemarks());

                item.setWorkOrder(workOrder);

                items.add(item);
            }
        }

        workOrder.setItems(items);

        return workOrder;
    }

    /**
     * Entity -> Response DTO
     */
    public WorkOrderResponseDTO toResponseDTO(WorkOrder workOrder) {

        if (workOrder == null) {
            return null;
        }

        // Create the list FIRST
        List<WorkOrderItemResponseDTO> itemResponses = new ArrayList<>();

        if (workOrder.getItems() != null) {

            for (WorkOrderItem item : workOrder.getItems()) {

                itemResponses.add(
                        WorkOrderItemResponseDTO.builder()
                                .id(item.getId())
                                .equipmentType(item.getEquipmentType())
                                .equipmentName(
                                        item.getEquipment() != null ? item.getEquipment().getEquipmentName() : null)
                                .driverName(item.getDriver() != null ? item.getDriver().getFullName() : null)
                                .quantity(item.getQuantity())
                                .totalHours(item.getTotalHours())
                                .hourlyRate(item.getHourlyRate())
                                .trips(item.getTrips())
                                .tripRate(item.getTripRate())
                                .startMeter(item.getStartMeter())
                                .endMeter(item.getEndMeter())
                                .dieselUsed(item.getDieselUsed())
                                .hoursAmount(item.getHoursAmount())
                                .tripAmount(item.getTripAmount())
                                .totalAmount(item.getTotalAmount())
                                .remarks(item.getRemarks())
                                .build());
            }
        }

        return WorkOrderResponseDTO.builder()
                .id(workOrder.getId())
                .workOrderNo(workOrder.getWorkOrderNo())
                .customerName(workOrder.getCustomer() != null ? workOrder.getCustomer().getName() : null)
                .workDate(workOrder.getWorkDate())
                .siteName(workOrder.getSiteName())
                .workDescription(workOrder.getWorkDescription())
                .remarks(workOrder.getRemarks())
                .status(workOrder.getStatus())
                .grandTotal(workOrder.getTotalAmount())
                .items(itemResponses)
                .build();
    }

    public List<WorkOrderResponseDTO> toResponseList(List<WorkOrder> workOrders) {

        if (workOrders == null || workOrders.isEmpty()) {
            return Collections.emptyList();
        }

        return workOrders.stream()
                .map(this::toResponseDTO)
                .toList();
    }
}