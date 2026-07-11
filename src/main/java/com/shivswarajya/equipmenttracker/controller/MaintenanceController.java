package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.MaintenanceRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.MaintenanceResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.enums.MaintenanceStatus;
import com.shivswarajya.equipmenttracker.mapper.MaintenanceMapper;
import com.shivswarajya.equipmenttracker.service.MaintenanceService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/maintenance")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MaintenanceController {

        private final MaintenanceService maintenanceService;
        private final MaintenanceMapper maintenanceMapper;

        @PostMapping
        public ApiResponse<MaintenanceResponseDTO> addMaintenance(
                        @Valid @RequestBody MaintenanceRequestDTO dto) {

                Maintenance maintenance = maintenanceService.addMaintenance(dto);

                return new ApiResponse<>(
                                true,
                                "Maintenance record added successfully",
                                maintenanceMapper.toResponse(maintenance));
        }

        @GetMapping
        public ApiResponse<List<MaintenanceResponseDTO>> getAllMaintenance() {

                List<MaintenanceResponseDTO> response = maintenanceService.getAllMaintenance()
                                .stream()
                                .map(maintenanceMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Maintenance records fetched successfully",
                                response);
        }

        @GetMapping("/{id}")
        public ApiResponse<MaintenanceResponseDTO> getMaintenance(
                        @PathVariable Long id) {

                return new ApiResponse<>(
                                true,
                                "Maintenance record fetched successfully",
                                maintenanceMapper.toResponse(
                                                maintenanceService.getMaintenance(id)));
        }

        @GetMapping("/equipment/{equipmentId}")
        public ApiResponse<List<MaintenanceResponseDTO>> getByEquipment(
                        @PathVariable Long equipmentId) {

                List<MaintenanceResponseDTO> response = maintenanceService.getByEquipment(equipmentId)
                                .stream()
                                .map(maintenanceMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Equipment maintenance history fetched",
                                response);
        }

        @GetMapping("/status/{status}")
        public ApiResponse<List<MaintenanceResponseDTO>> getByStatus(
                        @PathVariable MaintenanceStatus status) {

                List<MaintenanceResponseDTO> response = maintenanceService.getByStatus(status)
                                .stream()
                                .map(maintenanceMapper::toResponse)
                                .toList();

                return new ApiResponse<>(
                                true,
                                "Maintenance records fetched successfully",
                                response);
        }

        @DeleteMapping("/{id}")
        public ApiResponse<String> deleteMaintenance(
                        @PathVariable Long id) {

                maintenanceService.deleteMaintenance(id);

                return new ApiResponse<>(
                                true,
                                "Maintenance record deleted successfully",
                                null);
        }

}