package com.shivswarajya.equipmenttracker.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shivswarajya.equipmenttracker.dto.request.FuelRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.entity.Fuel;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.FuelRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.service.FuelService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class FuelServiceImpl implements FuelService {

    private final FuelRepository fuelRepository;
    private final WorkOrderRepository workOrderRepository;
    private final EquipmentRepository equipmentRepository;

    @Override
    public Fuel addFuel(FuelRequestDTO dto) {

        WorkOrder workOrder = workOrderRepository
                .findByIdWithDetails(dto.getWorkOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Work Order not found"));

        Equipment equipment = equipmentRepository.findById(dto.getEquipmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Equipment not found"));

        if (dto.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Fuel quantity must be greater than zero.");
        }

        if (dto.getRatePerLitre() <= 0) {
            throw new IllegalArgumentException(
                    "Rate per litre must be greater than zero.");
        }
        if (!workOrder.getEquipment().getId().equals(equipment.getId())) {
            throw new IllegalArgumentException(
                    "Selected equipment does not belong to this Work Order.");
        }
        Fuel fuel = Fuel.builder()
                .fuelEntryNo(generateFuelNumber())
                .workOrder(workOrder)
                .equipment(equipment)
                .fuelDate(LocalDate.now())
                .quantity(dto.getQuantity())
                .ratePerLitre(dto.getRatePerLitre())
                .totalAmount(
                        calculateTotalAmount(
                                dto.getQuantity(),
                                dto.getRatePerLitre()))
                .fuelStation(dto.getFuelStation())
                .remarks(dto.getRemarks())
                .build();

        Fuel saved = fuelRepository.save(fuel);

        return fuelRepository.findByIdWithDetails(saved.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Fuel entry not found"));
    }

    @Override
    public Fuel getFuel(Long id) {

        return fuelRepository.findByIdWithDetails(id)
                .orElseThrow(() -> new ResourceNotFoundException("Fuel entry not found"));
    }

    @Override
    public Fuel getByFuelEntryNo(String fuelEntryNo) {

        return fuelRepository.findByFuelEntryNo(fuelEntryNo)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Fuel entry not found"));
    }

    @Override
    public List<Fuel> getAllFuelEntries() {
        return fuelRepository.findAllWithDetails();
    }

    @Override
    public List<Fuel> getFuelByEquipment(Long equipmentId) {
        return fuelRepository.findByEquipmentIdWithDetails(equipmentId);
    }

    @Override
    public List<Fuel> getFuelByWorkOrder(Long workOrderId) {
        return fuelRepository.findByWorkOrderIdWithDetails(workOrderId);
    }

    @Override
    public void deleteFuel(Long id) {
        fuelRepository.delete(getFuel(id));
    }

    @Override
    public List<Fuel> getFuelByDate(LocalDate fuelDate) {
        return fuelRepository.findByFuelDateWithDetails(fuelDate);
    }

    private String generateFuelNumber() {

        Optional<Fuel> last = fuelRepository.findTopByOrderByIdDesc();

        if (last.isEmpty()) {
            return "FUEL-000001";
        }

        String lastNo = last.get().getFuelEntryNo();

        int number = Integer.parseInt(lastNo.substring(5));

        return String.format("FUEL-%06d", number + 1);
    }

    private double calculateTotalAmount(
            double quantity,
            double ratePerLitre) {

        return quantity * ratePerLitre;
    }

    public double getTotalFuelExpense() {

        return fuelRepository.findAll()
                .stream()
                .mapToDouble(Fuel::getTotalAmount)
                .sum();
    }
}