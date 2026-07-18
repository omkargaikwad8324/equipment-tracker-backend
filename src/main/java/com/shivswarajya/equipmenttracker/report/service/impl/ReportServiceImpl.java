package com.shivswarajya.equipmenttracker.report.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.entity.Fuel;
import com.shivswarajya.equipmenttracker.entity.Invoice;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.report.dto.DashboardReportDTO;
import com.shivswarajya.equipmenttracker.report.dto.EquipmentUtilizationDTO;
import com.shivswarajya.equipmenttracker.report.dto.ExpenseReportDTO;
import com.shivswarajya.equipmenttracker.report.dto.RevenueReportDTO;
import com.shivswarajya.equipmenttracker.report.service.ReportService;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.repository.DriverRepository;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.ExpenseRepository;
import com.shivswarajya.equipmenttracker.repository.FuelRepository;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.MaintenanceRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

        private final CustomerRepository customerRepository;
        private final EquipmentRepository equipmentRepository;
        private final DriverRepository driverRepository;
        private final InvoiceRepository invoiceRepository;
        private final ExpenseRepository expenseRepository;
        private final FuelRepository fuelRepository;
        private final MaintenanceRepository maintenanceRepository;
        private final WorkOrderRepository workOrderRepository;
        private final WorkOrderItemRepository workOrderItemRepository;

        @Override
        public DashboardReportDTO getDashboardReport() {

                double totalRevenue = invoiceRepository.getTotalRevenue();
                double receivedAmount = invoiceRepository.getTotalPaidAmount();

                double pendingAmount = invoiceRepository.getTotalPendingAmount();

                double fuelExpense = fuelRepository.getTotalFuelExpense();

                double maintenanceExpense = maintenanceRepository.getTotalMaintenanceExpense();

                double otherExpense = expenseRepository.getTotalMiscellaneousExpense();
                double totalExpense = fuelExpense
                                + maintenanceExpense
                                + otherExpense;

                double profit = totalRevenue - totalExpense;

                double profitPercentage = 0;

                if (totalRevenue > 0) {
                        profitPercentage = (profit * 100.0) / totalRevenue;
                }

                return DashboardReportDTO.builder()
                                .totalCustomers(customerRepository.count())
                                .totalEquipment(equipmentRepository.count())
                                .totalDrivers(driverRepository.count())
                                .totalInvoices(invoiceRepository.count())

                                .totalRevenue(totalRevenue)
                                .receivedAmount(receivedAmount)
                                .pendingAmount(pendingAmount)

                                .totalFuelExpense(fuelExpense)
                                .totalMaintenanceExpense(maintenanceExpense)
                                .totalExpenses(totalExpense)

                                .netProfit(profit)
                                .profitPercentage(profitPercentage)

                                .build();
        }

        @Override
        public RevenueReportDTO getRevenueReport() {

                double totalRevenue = invoiceRepository.findAll()
                                .stream()
                                .mapToDouble(Invoice::getGrandTotal)
                                .sum();

                double totalPaid = invoiceRepository.findAll()
                                .stream()
                                .mapToDouble(Invoice::getPaidAmount)
                                .sum();

                double totalPending = invoiceRepository.findAll()
                                .stream()
                                .mapToDouble(Invoice::getBalanceAmount)
                                .sum();
                double collectionPercentage = 0;

                if (totalRevenue > 0) {
                        collectionPercentage = (totalPaid * 100.0) / totalRevenue;
                }

                return RevenueReportDTO.builder()
                                .totalRevenue(totalRevenue)
                                .totalPaid(totalPaid)
                                .totalPending(totalPending)
                                .totalInvoices(invoiceRepository.count())
                                .collectionPercentage(collectionPercentage)
                                .build();
        }

        @Override
        public ExpenseReportDTO getExpenseReport() {

                double fuelExpense = fuelRepository.findAll()
                                .stream()
                                .mapToDouble(Fuel::getTotalAmount)
                                .sum();

                double maintenanceExpense = maintenanceRepository.findAll()
                                .stream()
                                .mapToDouble(Maintenance::getCost)
                                .sum();

                double miscellaneousExpense = expenseRepository.findAll()
                                .stream()
                                .mapToDouble(Expense::getAmount)
                                .sum();
                double totalExpense = fuelExpense
                                + maintenanceExpense
                                + miscellaneousExpense;

                String highest = "Fuel";

                if (maintenanceExpense > fuelExpense
                                && maintenanceExpense > miscellaneousExpense) {

                        highest = "Maintenance";
                }

                if (miscellaneousExpense > fuelExpense
                                && miscellaneousExpense > maintenanceExpense) {

                        highest = "Miscellaneous";
                }

                return ExpenseReportDTO.builder()
                                .fuelExpense(fuelExpense)
                                .maintenanceExpense(maintenanceExpense)
                                .miscellaneousExpense(miscellaneousExpense)
                                .totalExpense(totalExpense)
                                .highestExpenseType(highest)
                                .build();
        }

        @Override
        public List<EquipmentUtilizationDTO> getEquipmentUtilizationReport() {

                return equipmentRepository.findAll()
                                .stream()
                                .map(this::mapEquipmentReport)
                                .collect(Collectors.toList());
        }

        private EquipmentUtilizationDTO mapEquipmentReport(Equipment equipment) {

                double revenue = workOrderItemRepository
                                .findByEquipmentId(equipment.getId())
                                .stream()
                                .map(item -> item.getTotalAmount())
                                .filter(Objects::nonNull)
                                .mapToDouble(java.math.BigDecimal::doubleValue)
                                .sum();
                double workingHours = workOrderItemRepository
                                .findByEquipmentId(equipment.getId())
                                .stream()
                                .map(item -> item.getTotalHours())
                                .filter(Objects::nonNull)
                                .mapToDouble(Double::doubleValue)
                                .sum();

                double fuelExpense = fuelRepository
                                .findByEquipmentId(equipment.getId())
                                .stream()
                                .mapToDouble(Fuel::getTotalAmount)
                                .sum();

                double maintenanceExpense = maintenanceRepository
                                .findByEquipmentId(equipment.getId())
                                .stream()
                                .mapToDouble(Maintenance::getCost)
                                .sum();
                double profit = revenue
                                - fuelExpense
                                - maintenanceExpense;

                double profitPerHour = 0;

                if (workingHours > 0) {
                        profitPerHour = profit / workingHours;
                }
                return EquipmentUtilizationDTO.builder()
                                .equipmentId(equipment.getId())
                                .equipmentName(equipment.getEquipmentName())
                                .registrationNumber(equipment.getRegistrationNumber())
                                .totalWorkingHours(workingHours)
                                .totalRevenue(revenue)
                                .fuelExpense(fuelExpense)
                                .maintenanceExpense(maintenanceExpense)
                                .netProfit(profit)
                                .profitPerHour(profitPerHour)
                                .build();
        }

}