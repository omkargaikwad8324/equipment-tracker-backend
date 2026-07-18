package com.shivswarajya.equipmenttracker.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderSummaryDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;

public interface WorkOrderService {

    WorkOrderResponseDTO addWorkOrder(WorkOrderRequestDTO dto);

    WorkOrderResponseDTO updateWorkOrder(Long id, WorkOrderRequestDTO dto);

    void deleteWorkOrder(Long id);

    WorkOrderResponseDTO getWorkOrder(Long id);

    List<WorkOrderResponseDTO> getAllWorkOrders();

    List<WorkOrderResponseDTO> searchByCustomer(String customerName);

    List<WorkOrderSummaryDTO> getCustomerWorkOrders(Long customerId);

}