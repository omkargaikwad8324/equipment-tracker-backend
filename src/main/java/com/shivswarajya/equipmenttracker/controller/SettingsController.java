package com.shivswarajya.equipmenttracker.controller;

import com.shivswarajya.equipmenttracker.dto.request.SettingsDTO;
import com.shivswarajya.equipmenttracker.dto.response.SettingsResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Settings;
import com.shivswarajya.equipmenttracker.mapper.SettingsMapper;
import com.shivswarajya.equipmenttracker.report.service.SettingsService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@CrossOrigin(origins = "*")
public class SettingsController {

    private final SettingsService settingsService;
    private final SettingsMapper settingsMapper;

    public SettingsController(
            SettingsService settingsService,
            SettingsMapper settingsMapper) {

        this.settingsService = settingsService;
        this.settingsMapper = settingsMapper;
    }

    @PostMapping("/init")
    public ApiResponse<SettingsResponseDTO> initialize(
            @Valid @RequestBody SettingsDTO dto) {

        Settings settings = settingsService.initialize(dto);

        return new ApiResponse<>(
                true,
                "Settings initialized successfully",
                settingsMapper.toResponse(settings));
    }

    @GetMapping
    public ApiResponse<SettingsResponseDTO> getSettings() {

        return new ApiResponse<>(
                true,
                "Settings fetched successfully",
                settingsMapper.toResponse(
                        settingsService.getSettings()));
    }

    @PutMapping
    public ApiResponse<SettingsResponseDTO> updateSettings(
            @Valid @RequestBody SettingsDTO dto) {

        Settings settings =
                settingsService.updateSettings(dto);

        return new ApiResponse<>(
                true,
                "Settings updated successfully",
                settingsMapper.toResponse(settings));
    }
}