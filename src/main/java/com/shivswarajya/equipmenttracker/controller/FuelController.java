package com.shivswarajya.equipmenttracker.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.FuelRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.FuelResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Fuel;
import com.shivswarajya.equipmenttracker.mapper.FuelMapper;
import com.shivswarajya.equipmenttracker.service.FuelService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/fuel")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class FuelController {

        private final FuelService fuelService;
        private final FuelMapper fuelMapper;

        @PostMapping
        public ApiResponse<FuelResponseDTO> addFuel(
                        @Valid @RequestBody FuelRequestDTO dto) {

                Fuel fuel = fuelService.addFuel(dto);

                return new ApiResponse<>(
                                true,
                                "Fuel entry added successfully",
                                fuelMapper.toResponse(fuel));
        }

        @GetMapping
        public ApiResponse<List<FuelResponseDTO>> getAllFuelEntries() {

                List<FuelResponseDTO> response = fuelService.getAllFuelEntries()
                                .stream()
                                .map(fuelMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Fuel entries fetched successfully",
                                response);
        }

        @GetMapping("/{id}")
        public ApiResponse<FuelResponseDTO> getFuel(@PathVariable Long id) {

                return new ApiResponse<>(
                                true,
                                "Fuel entry fetched successfully",
                                fuelMapper.toResponse(fuelService.getFuel(id)));
        }

        @GetMapping("/equipment/{equipmentId}")
        public ApiResponse<List<FuelResponseDTO>> getByEquipment(
                        @PathVariable Long equipmentId) {

                List<FuelResponseDTO> response = fuelService
                                .getFuelByEquipment(equipmentId)
                                .stream()
                                .map(fuelMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Equipment fuel history fetched",
                                response);
        }

        @GetMapping("/work-order/{workOrderId}")
        public ApiResponse<List<FuelResponseDTO>> getByWorkOrder(
                        @PathVariable Long workOrderId) {

                List<FuelResponseDTO> response = fuelService
                                .getFuelByWorkOrder(workOrderId)
                                .stream()
                                .map(fuelMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Work Order fuel history fetched",
                                response);
        }

        @GetMapping("/date/{fuelDate}")
        public ApiResponse<List<FuelResponseDTO>> getByDate(
                        @PathVariable LocalDate fuelDate) {

                List<FuelResponseDTO> response = fuelService.getFuelByDate(fuelDate)
                                .stream()
                                .map(fuelMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Fuel entries fetched successfully",
                                response);
        }

        @DeleteMapping("/{id}")
        public ApiResponse<String> deleteFuel(@PathVariable Long id) {

                fuelService.deleteFuel(id);

                return new ApiResponse<>(
                                true,
                                "Fuel entry deleted successfully",
                                null);
        }
}