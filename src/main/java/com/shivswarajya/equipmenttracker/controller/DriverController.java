package com.shivswarajya.equipmenttracker.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dto.request.DriverRequestDTO;
import com.shivswarajya.equipmenttracker.dto.response.DriverResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Driver;
import com.shivswarajya.equipmenttracker.mapper.DriverMapper;
import com.shivswarajya.equipmenttracker.service.DriverService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/drivers")
@CrossOrigin(origins = "*")
public class DriverController {

    private final DriverService driverService;
    private final DriverMapper driverMapper;

    public DriverController(
            DriverService driverService,
            DriverMapper driverMapper) {

        this.driverService = driverService;
        this.driverMapper = driverMapper;
    }

    @PostMapping
    public ApiResponse<DriverResponseDTO> addDriver(
            @Valid @RequestBody DriverRequestDTO dto) {

        Driver driver = driverService.addDriver(dto);

        return new ApiResponse<>(
                true,
                "Driver added successfully",
                driverMapper.toResponse(driver));
    }

    @GetMapping
    public ApiResponse<List<DriverResponseDTO>> getAllDrivers() {

        List<DriverResponseDTO> response = driverService.getAllDrivers()
                .stream()
                .map(driverMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Drivers fetched successfully",
                response);
    }

    @GetMapping("/{id}")
    public ApiResponse<DriverResponseDTO> getDriver(
            @PathVariable Long id) {

        return new ApiResponse<>(
                true,
                "Driver fetched successfully",
                driverMapper.toResponse(driverService.getDriver(id)));
    }

    @GetMapping("/search")
    public ApiResponse<List<DriverResponseDTO>> searchDrivers(
            @RequestParam String keyword) {

        List<DriverResponseDTO> response = driverService
                .searchDrivers(keyword)
                .stream()
                .map(driverMapper::toResponse)
                .toList();

        return new ApiResponse<>(
                true,
                "Drivers fetched successfully",
                response);
    }

    @PutMapping("/{id}")
    public ApiResponse<DriverResponseDTO> updateDriver(
            @PathVariable Long id,
            @Valid @RequestBody DriverRequestDTO dto) {

        Driver driver = driverService.updateDriver(id, dto);

        return new ApiResponse<>(
                true,
                "Driver updated successfully",
                driverMapper.toResponse(driver));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDriver(
            @PathVariable Long id) {

        driverService.deleteDriver(id);

        return new ApiResponse<>(
                true,
                "Driver deleted successfully",
                null);
    }
}