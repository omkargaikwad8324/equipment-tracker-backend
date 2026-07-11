package com.shivswarajya.equipmenttracker.service;

import java.time.LocalDate;
import java.util.List;

import com.shivswarajya.equipmenttracker.dto.request.FuelRequestDTO;
import com.shivswarajya.equipmenttracker.entity.Fuel;

public interface FuelService {

    Fuel addFuel(FuelRequestDTO dto);

    Fuel getFuel(Long id);

    List<Fuel> getAllFuelEntries();

    List<Fuel> getFuelByEquipment(Long equipmentId);

    List<Fuel> getFuelByWorkOrder(Long workOrderId);

    void deleteFuel(Long id);

    Fuel getByFuelEntryNo(String fuelEntryNo);

List<Fuel> getFuelByDate(LocalDate fuelDate);
}