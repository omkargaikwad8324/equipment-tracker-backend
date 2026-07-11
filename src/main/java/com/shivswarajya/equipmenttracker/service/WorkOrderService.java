package com.shivswarajya.equipmenttracker.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;

public interface WorkOrderService {

    WorkOrder addWorkOrder(WorkOrderRequestDTO dto);

    WorkOrder updateWorkOrder(Long id, WorkOrderRequestDTO dto);

    void deleteWorkOrder(Long id);

    WorkOrder getWorkOrder(Long id);

    List<WorkOrder> getAllWorkOrders();

    List<WorkOrder> searchByCustomer(String customerName);

}