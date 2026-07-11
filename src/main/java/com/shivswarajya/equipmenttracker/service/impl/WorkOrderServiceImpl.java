package com.shivswarajya.equipmenttracker.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shivswarajya.equipmenttracker.dto.request.WorkOrderRequestDTO;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.service.WorkOrderService;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

import com.shivswarajya.equipmenttracker.entity.Customer;
import com.shivswarajya.equipmenttracker.entity.Driver;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.enums.DriverStatus;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService {

        private final WorkOrderRepository workOrderRepository;
        private final CustomerRepository customerRepository;
        private final EquipmentRepository equipmentRepository;
        private final DriverRepository driverRepository;

        private String generateWorkOrderNumber() {

                return workOrderRepository.findTopByOrderByIdDesc()
                                .map(last -> {
                                        String lastNo = last.getWorkOrderNo();
                                        int number = Integer.parseInt(lastNo.substring(3));
                                        return String.format("WO-%06d", number + 1);
                                })
                                .orElse("WO-000001");
        }

        private Double calculateTotalHours(
                        Double startMeter,
                        Double endMeter) {

                if (startMeter == null || endMeter == null) {
                        throw new BadRequestException(
                                        "Meter readings are required.");
                }

                if (startMeter < 0 || endMeter < 0) {
                        throw new BadRequestException(
                                        "Meter readings cannot be negative.");
                }

                if (endMeter <= startMeter) {
                        throw new BadRequestException(
                                        "End meter must be greater than start meter.");
                }

                return endMeter - startMeter;
        }

        private BigDecimal calculateTotalAmount(
                        Double totalHours,
                        BigDecimal hourlyRate) {

                if (hourlyRate == null) {
                        return BigDecimal.ZERO;
                }

                return hourlyRate.multiply(BigDecimal.valueOf(totalHours));
        }

        private void validateActiveAssignment(
                        Long equipmentId,
                        Long driverId) {

                List<WorkStatus> activeStatuses = List.of(
                                WorkStatus.PENDING,
                                WorkStatus.IN_PROGRESS);

                if (workOrderRepository.existsByEquipmentIdAndStatusIn(
                                equipmentId,
                                activeStatuses)) {

                        throw new BadRequestException(
                                        "Equipment already has an active Work Order.");
                }

                if (workOrderRepository.existsByDriverIdAndStatusIn(
                                driverId,
                                activeStatuses)) {

                        throw new BadRequestException(
                                        "Driver already has an active Work Order.");
                }
        }

        @Override
        public WorkOrder addWorkOrder(WorkOrderRequestDTO dto) {

                Customer customer = customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

                Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));

                if (equipment.getStatus() != EquipmentStatus.WORKING) {
                        throw new BadRequestException(
                                        "Equipment is not available for work.");
                }

                Driver driver = driverRepository.findById(dto.getDriverId())
                                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));

                if (driver.getStatus() != DriverStatus.ACTIVE) {
                        throw new BadRequestException(
                                        "Driver is not active.");
                }

                validateActiveAssignment(dto.getEquipmentId(), dto.getDriverId());

                Double totalHours = calculateTotalHours(
                                dto.getStartMeter(),
                                dto.getEndMeter());

                BigDecimal totalAmount = calculateTotalAmount(
                                totalHours,
                                equipment.getHourlyRate());

                WorkOrder workOrder = WorkOrder.builder()

                                .workOrderNo(generateWorkOrderNumber())

                                .customer(customer)

                                .equipment(equipment)

                                .driver(driver)

                                .workDate(dto.getWorkDate())

                                .siteName(dto.getSiteName())

                                .workDescription(dto.getWorkDescription())

                                .startMeter(dto.getStartMeter())

                                .endMeter(dto.getEndMeter())

                                .totalHours(totalHours)

                                .hourlyRate(equipment.getHourlyRate())

                                .totalAmount(totalAmount)

                                .dieselUsed(dto.getDieselUsed())

                                .remarks(dto.getRemarks())

                                .status(
                                                dto.getStatus() != null
                                                                ? dto.getStatus()
                                                                : WorkStatus.PENDING)
                                .build();

                equipment.setStatus(EquipmentStatus.WORKING);
                equipmentRepository.save(equipment);

                return workOrderRepository.save(workOrder);
        }

        @Transactional(readOnly = true)
        @Override
        public WorkOrder getWorkOrder(Long id) {

                return workOrderRepository.findByIdWithDetails(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));
        }

        @Transactional(readOnly = true)
        @Override
        public List<WorkOrder> getAllWorkOrders() {

                return workOrderRepository.findAllWithDetails();
        }

        @Transactional(readOnly = true)
        @Override
        public List<WorkOrder> searchByCustomer(String customerName) {

                return workOrderRepository
                                .findByCustomer_NameContainingIgnoreCase(customerName);
        }

        @Override
        public WorkOrder updateWorkOrder(Long id, WorkOrderRequestDTO dto) {

                WorkOrder workOrder = workOrderRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));

                Customer customer = customerRepository.findById(dto.getCustomerId())
                                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
                Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));

                if (equipment.getStatus() != EquipmentStatus.WORKING) {
                        throw new BadRequestException(
                                        "Equipment is not available for work.");
                }
                Driver driver = driverRepository.findById(dto.getDriverId())
                                .orElseThrow(() -> new ResourceNotFoundException("Driver not found"));
                if (driver.getStatus() != DriverStatus.ACTIVE) {
                        throw new BadRequestException(
                                        "Driver is not active.");
                }
                validateActiveAssignmentForUpdate(
                                equipment.getId(),
                                driver.getId(),
                                workOrder.getId());

                Double totalHours = calculateTotalHours(
                                dto.getStartMeter(),
                                dto.getEndMeter());

                BigDecimal totalAmount = calculateTotalAmount(
                                totalHours,
                                equipment.getHourlyRate());

                workOrder.setCustomer(customer);
                workOrder.setEquipment(equipment);
                workOrder.setDriver(driver);

                workOrder.setWorkDate(dto.getWorkDate());
                workOrder.setSiteName(dto.getSiteName());
                workOrder.setWorkDescription(dto.getWorkDescription());

                workOrder.setStartMeter(dto.getStartMeter());
                workOrder.setEndMeter(dto.getEndMeter());

                workOrder.setTotalHours(totalHours);

                workOrder.setHourlyRate(equipment.getHourlyRate());

                workOrder.setTotalAmount(totalAmount);

                workOrder.setDieselUsed(dto.getDieselUsed());
                workOrder.setStatus(dto.getStatus() != null ? dto.getStatus() : WorkStatus.PENDING);
                workOrder.setRemarks(dto.getRemarks());

                return workOrderRepository.save(workOrder);
        }

        private void validateActiveAssignmentForUpdate(
                        Long equipmentId,
                        Long driverId,
                        Long workOrderId) {

                List<WorkStatus> activeStatuses = List.of(
                                WorkStatus.PENDING,
                                WorkStatus.IN_PROGRESS);

                if (workOrderRepository.existsByEquipmentIdAndStatusInAndIdNot(
                                equipmentId,
                                activeStatuses,
                                workOrderId)) {

                        throw new BadRequestException(
                                        "Equipment already has an active Work Order.");
                }

                if (workOrderRepository.existsByDriverIdAndStatusInAndIdNot(
                                driverId,
                                activeStatuses,
                                workOrderId)) {

                        throw new BadRequestException(
                                        "Driver already has an active Work Order.");
                }

        }

        @Override
        public void deleteWorkOrder(Long id) {

                WorkOrder workOrder = workOrderRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));

                Equipment equipment = workOrder.getEquipment();

                equipment.setStatus(EquipmentStatus.IDLE);

                equipmentRepository.save(equipment);

                workOrderRepository.delete(workOrder);
        }
}