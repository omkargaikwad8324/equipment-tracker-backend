package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.mapper.WorkOrderMapper;
import com.shivswarajya.equipmenttracker.service.WorkOrderService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WorkOrderController {

    private final WorkOrderService workOrderService;
    private final WorkOrderMapper workOrderMapper;

    @PostMapping
    public ApiResponse<WorkOrderResponseDTO> addWorkOrder(
            @Valid @RequestBody WorkOrderRequestDTO dto) {

        WorkOrder workOrder = workOrderService.addWorkOrder(dto);

        return new ApiResponse<>(
                true,
                "Work Order created successfully",
                workOrderMapper.toResponse(workOrder));
    }

    @GetMapping
    public ApiResponse<List<WorkOrderResponseDTO>> getAllWorkOrders() {

        List<WorkOrderResponseDTO> response = workOrderService
                .getAllWorkOrders()
                .stream()
                .map(workOrderMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Work Orders fetched successfully",
                response);
    }

    @GetMapping("/{id}")
    public ApiResponse<WorkOrderResponseDTO> getWorkOrder(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Work Order fetched successfully",
                workOrderMapper.toResponse(
                        workOrderService.getWorkOrder(id)));
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkOrderResponseDTO> updateWorkOrder(
            @PathVariable Long id,
            @Valid @RequestBody WorkOrderRequestDTO dto) {

        WorkOrder workOrder =
                workOrderService.updateWorkOrder(id, dto);

        return new ApiResponse<>(
                true,
                "Work Order updated successfully",
                workOrderMapper.toResponse(workOrder));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteWorkOrder(
            @PathVariable Long id) {

        workOrderService.deleteWorkOrder(id);

        return new ApiResponse<>(
                true,
                "Work Order deleted successfully",
                null);
    }

    @GetMapping("/search")
    public ApiResponse<List<WorkOrderResponseDTO>> searchCustomer(
            @RequestParam String customerName) {

        List<WorkOrderResponseDTO> response =
                workOrderService.searchByCustomer(customerName)
                        .stream()
                        .map(workOrderMapper::toResponse)
                        .toList();

        return new ApiResponse<>(
                true,
                "Work Orders fetched successfully",
                response);
    }
}