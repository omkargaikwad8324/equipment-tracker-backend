package com.shivswarajya.equipmenttracker.report.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.shivswarajya.equipmenttracker.report.dto.*;
import com.shivswarajya.equipmenttracker.report.service.ReportService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    public ApiResponse<DashboardReportDTO> dashboard() {

        return new ApiResponse<>(
                true,
                "Dashboard Report",
                reportService.getDashboardReport());
    }

    @GetMapping("/revenue")
    public ApiResponse<RevenueReportDTO> revenue() {

        return new ApiResponse<>(
                true,
                "Revenue Report",
                reportService.getRevenueReport());
    }

    @GetMapping("/expense")
    public ApiResponse<ExpenseReportDTO> expense() {

        return new ApiResponse<>(
                true,
                "Expense Report",
                reportService.getExpenseReport());
    }

    @GetMapping("/equipment")
    public ApiResponse<List<EquipmentUtilizationDTO>> equipment() {

        return new ApiResponse<>(
                true,
                "Equipment Utilization",
                reportService.getEquipmentUtilizationReport());
    }
}