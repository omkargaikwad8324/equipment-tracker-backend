package com.shivswarajya.equipmenttracker.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderItemRequestDTO;
import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderResponseDTO;
import com.shivswarajya.equipmenttracker.dto.response.WorkOrderSummaryDTO;
import com.shivswarajya.equipmenttracker.entity.Customer;
import com.shivswarajya.equipmenttracker.entity.Driver;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.entity.WorkOrderItem;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.mapper.WorkOrderMapper;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.repository.DriverRepository;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.FuelRepository;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.service.WorkOrderCalculationService;
import com.shivswarajya.equipmenttracker.service.WorkOrderService;
import org.springframework.dao.DataIntegrityViolationException;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

        private final WorkOrderRepository workOrderRepository;
        private final CustomerRepository customerRepository;
        private final EquipmentRepository equipmentRepository;
        private final DriverRepository driverRepository;
        private final WorkOrderMapper workOrderMapper;
        private final WorkOrderCalculationService calculationService;
        private final FuelRepository fuelRepository;
        private final InvoiceRepository invoiceRepository;

        private Customer getCustomer(Long customerId) {

                return customerRepository.findById(customerId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Customer not found with ID: " + customerId));
        }

        private Equipment getEquipment(Long equipmentId) {

                return equipmentRepository.findById(equipmentId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Equipment not found with ID: " + equipmentId));
        }

        private Driver getDriver(Long driverId) {

                return driverRepository.findById(driverId)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Driver not found with ID: " + driverId));
        }

        private WorkOrder getExistingWorkOrder(Long id) {

                return workOrderRepository.findWithItemsById(id)
                                .orElseThrow(() -> new ResourceNotFoundException(
                                                "Work Order not found with ID: " + id));
        }

        private String generateWorkOrderNumber() {

                return "WO-" + System.currentTimeMillis();
        }

        @Override
        public WorkOrderResponseDTO addWorkOrder(WorkOrderRequestDTO dto) {

                Customer customer = getCustomer(dto.getCustomerId());

                WorkOrder workOrder = workOrderMapper.toEntity(dto);

                workOrder.setCustomer(customer);

                workOrder.setWorkOrderNo(generateWorkOrderNumber());

                for (int i = 0; i < dto.getItems().size(); i++) {

                        WorkOrderItem item = workOrder.getItems().get(i);

                        var itemDTO = dto.getItems().get(i);

                        if (itemDTO.getEquipmentId() != null) {

                                item.setEquipment(
                                                getEquipment(itemDTO.getEquipmentId()));
                        }

                        if (itemDTO.getDriverId() != null) {

                                item.setDriver(
                                                getDriver(itemDTO.getDriverId()));
                        }

                        item.setWorkOrder(workOrder);
                }

                calculationService.calculateWorkOrder(workOrder);

                WorkOrder saved = workOrderRepository.save(workOrder);

                return workOrderMapper.toResponseDTO(saved);
        }

        @Override
        public WorkOrderResponseDTO updateWorkOrder(Long id, WorkOrderRequestDTO dto) {

                WorkOrder workOrder = getExistingWorkOrder(id);

                Customer customer = getCustomer(dto.getCustomerId());

                workOrder.setCustomer(customer);
                workOrder.setWorkDate(dto.getWorkDate());
                workOrder.setSiteName(dto.getSiteName());
                workOrder.setWorkDescription(dto.getWorkDescription());
                workOrder.setRemarks(dto.getRemarks());
                workOrder.setStatus(dto.getStatus());

                // Remove existing items
                workOrder.getItems().clear();

                // Add updated items
                for (WorkOrderItemRequestDTO itemDTO : dto.getItems()) {

                        WorkOrderItem item = new WorkOrderItem();

                        item.setEquipmentType(itemDTO.getEquipmentType());

                        if (itemDTO.getEquipmentId() != null) {
                                item.setEquipment(getEquipment(itemDTO.getEquipmentId()));
                        }

                        if (itemDTO.getDriverId() != null) {
                                item.setDriver(getDriver(itemDTO.getDriverId()));
                        }

                        item.setQuantity(itemDTO.getQuantity());
                        item.setTotalHours(itemDTO.getTotalHours());
                        item.setHourlyRate(itemDTO.getHourlyRate());
                        item.setTrips(itemDTO.getTrips());
                        item.setTripRate(itemDTO.getTripRate());
                        item.setStartMeter(itemDTO.getStartMeter());
                        item.setEndMeter(itemDTO.getEndMeter());
                        item.setDieselUsed(itemDTO.getDieselUsed());
                        item.setRemarks(itemDTO.getRemarks());

                        workOrder.addItem(item);
                }

                calculationService.calculateWorkOrder(workOrder);

                WorkOrder updated = workOrderRepository.save(workOrder);

                return workOrderMapper.toResponseDTO(updated);
        }

        @Override
        @Transactional(readOnly = true)
        public WorkOrderResponseDTO getWorkOrder(Long id) {

                WorkOrder workOrder = workOrderRepository.findWithItemsById(id)
                                .orElseThrow(() -> new RuntimeException("Work Order not found : " + id));

                return workOrderMapper.toResponseDTO(workOrder);
        }

        @Override
        @Transactional(readOnly = true)
        public List<WorkOrderResponseDTO> getAllWorkOrders() {

                List<WorkOrder> workOrders = workOrderRepository.findAll();

                return workOrderMapper.toResponseList(workOrders);
        }

        @Override
        @Transactional(readOnly = true)
        public List<WorkOrderResponseDTO> searchByCustomer(String customerName) {

                List<WorkOrder> workOrders = workOrderRepository
                                .findByCustomer_NameContainingIgnoreCaseOrderByWorkDateDesc(customerName);

                return workOrderMapper.toResponseList(workOrders);
        }

       @Override
public void deleteWorkOrder(Long id) {

    WorkOrder workOrder = getExistingWorkOrder(id);

    try {
        workOrderRepository.delete(workOrder);
        workOrderRepository.flush(); // Execute DELETE immediately

    } catch (DataIntegrityViolationException ex) {

        throw new IllegalStateException(
                "Cannot delete Work Order because it is linked to Fuel Entries, Invoices, or other records.");
    }
}

        @Override
        @Transactional(readOnly = true)
        public List<WorkOrderSummaryDTO> getCustomerWorkOrders(Long customerId) {

                List<WorkOrder> workOrders = workOrderRepository.findByCustomerIdOrderByWorkDateDesc(customerId);

                return workOrders.stream()
                                .map(workOrder -> WorkOrderSummaryDTO.builder()
                                                .id(workOrder.getId())
                                                .workOrderNo(workOrder.getWorkOrderNo())
                                                .workDate(workOrder.getWorkDate())
                                                .customerName(
                                                                workOrder.getCustomer() != null
                                                                                ? workOrder.getCustomer().getName()
                                                                                : null)
                                                .siteName(workOrder.getSiteName())
                                                .totalEquipment(
                                                                workOrder.getItems() != null
                                                                                ? workOrder.getItems().size()
                                                                                : 0)
                                                .grandTotal(workOrder.getTotalAmount())
                                                .status(workOrder.getStatus())
                                                .build())
                                .toList();
        }

}