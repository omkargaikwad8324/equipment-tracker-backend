package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.EquipmentRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.EquipmentResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.mapper.EquipmentMapper;
import com.shivswarajya.equipmenttracker.service.EquipmentService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/equipment")
@CrossOrigin(origins = "*")
public class EquipmentController {

    private final EquipmentService equipmentService;
    private final EquipmentMapper equipmentMapper;

    public EquipmentController(
            EquipmentService equipmentService,
            EquipmentMapper equipmentMapper) {

        this.equipmentService = equipmentService;
        this.equipmentMapper = equipmentMapper;
    }

    @PostMapping
    public ApiResponse<EquipmentResponseDTO> addEquipment(
            @Valid @RequestBody EquipmentRequestDTO dto) {

        Equipment equipment = equipmentService.addEquipment(dto);

        return new ApiResponse<>(
                true,
                "Equipment added successfully",
                equipmentMapper.toResponse(equipment));
    }

    @GetMapping
    public ApiResponse<List<EquipmentResponseDTO>> getAllEquipment() {

        List<EquipmentResponseDTO> response = equipmentService.getAllEquipment()
                .stream()
                .map(equipmentMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Equipment fetched successfully",
                response);
    }

    @GetMapping("/{id}")
    public ApiResponse<EquipmentResponseDTO> getEquipment(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Equipment fetched successfully",
                equipmentMapper.toResponse(
                        equipmentService.getEquipment(id)));
    }

    @GetMapping("/search")
    public ApiResponse<List<EquipmentResponseDTO>> searchEquipment(
            @RequestParam String keyword) {

        List<EquipmentResponseDTO> response = equipmentService
                .searchEquipment(keyword)
                .stream()
                .map(equipmentMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Equipment fetched successfully",
                response);
    }

    @PutMapping("/{id}")
    public ApiResponse<EquipmentResponseDTO> updateEquipment(
            @PathVariable Long id,
            @Valid @RequestBody EquipmentRequestDTO dto) {

        return new ApiResponse<>(
                true,
                "Equipment updated successfully",
                equipmentMapper.toResponse(
                        equipmentService.updateEquipment(id, dto)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteEquipment(
            @PathVariable Long id) {

        equipmentService.deleteEquipment(id);

        return new ApiResponse<>(
                true,
                "Equipment deleted successfully",
                null);
    }
}