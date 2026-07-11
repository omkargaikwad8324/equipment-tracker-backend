package com.shivswarajya.equipmenttracker.dashboard.controller;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.dashboard.dto.DashboardSummaryDTO;
import com.shivswarajya.equipmenttracker.dashboard.service.DashboardService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    public ApiResponse<DashboardSummaryDTO> getDashboard() {

        return new ApiResponse<>(
                true,
                "Dashboard loaded successfully",
                dashboardService.getDashboardSummary());
    }
}