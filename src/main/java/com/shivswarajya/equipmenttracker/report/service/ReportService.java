package com.shivswarajya.equipmenttracker.report.service;

import java.util.List;

import com.shivswarajya.equipmenttracker.report.dto.DashboardReportDTO;
import com.shivswarajya.equipmenttracker.report.dto.EquipmentUtilizationDTO;
import com.shivswarajya.equipmenttracker.report.dto.ExpenseReportDTO;
import com.shivswarajya.equipmenttracker.report.dto.RevenueReportDTO;

public interface ReportService {

    DashboardReportDTO getDashboardReport();

    RevenueReportDTO getRevenueReport();

    ExpenseReportDTO getExpenseReport();

    List<EquipmentUtilizationDTO> getEquipmentUtilizationReport();

}