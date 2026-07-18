package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderSummaryDTO;
import com.shivswarajya.equipmenttracker.service.WorkOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/work-orders")
@RequiredArgsConstructor
@Validated
@CrossOrigin(origins = "*")
public class WorkOrderController {

        private final WorkOrderService workOrderService;

        @PostMapping
        public ResponseEntity<WorkOrderResponseDTO> createWorkOrder(
                        @Valid @RequestBody WorkOrderRequestDTO requestDTO) {

                WorkOrderResponseDTO response = workOrderService.addWorkOrder(requestDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }

        @PutMapping("/{id}")
        public ResponseEntity<WorkOrderResponseDTO> updateWorkOrder(
                        @PathVariable Long id,
                        @Valid @RequestBody WorkOrderRequestDTO requestDTO) {

                return ResponseEntity.ok(
                                workOrderService.updateWorkOrder(id, requestDTO));
        }

        @GetMapping("/{id}")
        public ResponseEntity<WorkOrderResponseDTO> getWorkOrder(
                        @PathVariable Long id) {

                return ResponseEntity.ok(
                                workOrderService.getWorkOrder(id));
        }

        @GetMapping
        public ResponseEntity<List<WorkOrderResponseDTO>> getAllWorkOrders() {

                return ResponseEntity.ok(
                                workOrderService.getAllWorkOrders());
        }

        @GetMapping("/search")
        public ResponseEntity<List<WorkOrderResponseDTO>> searchByCustomer(
                        @RequestParam String customerName) {

                return ResponseEntity.ok(
                                workOrderService.searchByCustomer(customerName));
        }

        @GetMapping("/customer/{customerId}")
        public ResponseEntity<List<WorkOrderSummaryDTO>> getCustomerWorkOrders(
                        @PathVariable Long customerId) {

                return ResponseEntity.ok(
                                workOrderService.getCustomerWorkOrders(customerId));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteWorkOrder(
                        @PathVariable Long id) {

                workOrderService.deleteWorkOrder(id);

                return ResponseEntity.noContent().build();
        }

}