package com.shivswarajya.equipmenttracker.dashboard.service.impl;

import java.time.LocalDate;
import java.time.YearMonth;

import org.springframework.stereotype.Service;

import com.shivswarajya.equipmenttracker.dashboard.dto.DashboardSummaryDTO;
import com.shivswarajya.equipmenttracker.dashboard.service.DashboardService;
import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.entity.Fuel;
import com.shivswarajya.equipmenttracker.entity.Invoice;
import com.shivswarajya.equipmenttracker.entity.Maintenance;
import com.shivswarajya.equipmenttracker.entity.WorkOrder;
import com.shivswarajya.equipmenttracker.enums.EquipmentStatus;
import com.shivswarajya.equipmenttracker.enums.WorkStatus;
import com.shivswarajya.equipmenttracker.repository.CustomerRepository;
import com.shivswarajya.equipmenttracker.repository.DriverRepository;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.ExpenseRepository;
import com.shivswarajya.equipmenttracker.repository.FuelRepository;
import com.shivswarajya.equipmenttracker.repository.InvoiceRepository;
import com.shivswarajya.equipmenttracker.repository.MaintenanceRepository;
import com.shivswarajya.equipmenttracker.repository.PaymentRepository;
import com.shivswarajya.equipmenttracker.repository.WorkOrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

        private final CustomerRepository customerRepository;
        private final EquipmentRepository equipmentRepository;
        private final DriverRepository driverRepository;
        private final WorkOrderRepository workOrderRepository;
        private final InvoiceRepository invoiceRepository;
        private final FuelRepository fuelRepository;
        private final MaintenanceRepository maintenanceRepository;
        private final ExpenseRepository expenseRepository;
        private final PaymentRepository paymentRepository;

        @Override
        public DashboardSummaryDTO getDashboardSummary() {

                double totalRevenue = invoiceRepository.getTotalRevenue();

                double receivedAmount = invoiceRepository.getTotalPaidAmount();

                double pendingAmount = invoiceRepository.getTotalPendingAmount();
                double todayRevenue = invoiceRepository
                                .findByInvoiceDate(LocalDate.now())
                                .stream()
                                .mapToDouble(Invoice::getGrandTotal)
                                .sum();

                YearMonth currentMonth = YearMonth.now();

                long working = equipmentRepository.countByStatus(EquipmentStatus.WORKING);

                long idle = equipmentRepository.countByStatus(EquipmentStatus.IDLE);

                long maintenance = equipmentRepository.countByStatus(
                                EquipmentStatus.UNDER_MAINTENANCE);

                long breakdown = equipmentRepository.countByStatus(
                                EquipmentStatus.BREAKDOWN);

                double monthlyRevenue = invoiceRepository.findAll()
                                .stream()
                                .filter(i -> YearMonth.from(i.getInvoiceDate()).equals(currentMonth))
                                .mapToDouble(Invoice::getGrandTotal)
                                .sum();
                double todayHours = workOrderRepository.getTodayWorkingHours();

                double monthlyHours = workOrderRepository.getCurrentMonthWorkingHours();

                long completed = workOrderRepository.countCompletedWorkOrders();

                long pending = workOrderRepository.countPendingWorkOrders();

                long inProgress = workOrderRepository.countInProgressWorkOrders();
                double completionPercentage = 0;

                if (workOrderRepository.count() > 0) {

                        completionPercentage = (completed * 100.0)
                                        / workOrderRepository.count();
                }
                // Expense Calculations
                double fuelExpense = fuelRepository.getTotalFuelExpense();
                double maintenanceExpense = maintenanceRepository.getTotalMaintenanceExpense();

                double miscellaneousExpense = expenseRepository.getTotalMiscellaneousExpense();
                double totalExpenses = fuelExpense
                                + maintenanceExpense
                                + miscellaneousExpense;

                double netProfit = totalRevenue - totalExpenses;

                return DashboardSummaryDTO.builder()

                                .todayRevenue(todayRevenue)
                                .monthlyRevenue(monthlyRevenue)
                                .totalRevenue(totalRevenue)

                                .receivedAmount(receivedAmount)
                                .pendingAmount(pendingAmount)

                                .todayHours(todayHours)
                                .monthlyHours(monthlyHours)

                                .totalCustomers(customerRepository.count())
                                .totalEquipment(equipmentRepository.count())
                                .totalDrivers(driverRepository.count())

                                .workingEquipment(working)
                                .idleEquipment(idle)
                                .maintenanceEquipment(maintenance)
                                .breakdownEquipment(breakdown)

                                .totalWorkOrders(workOrderRepository.count())
                                .completedWorkOrders(completed)
                                .pendingWorkOrders(pending)
                                .inProgressWorkOrders(inProgress)

                                .totalInvoices(invoiceRepository.count())
                                .totalPayments(paymentRepository.count())

                                .fuelExpense(fuelExpense)
                                .maintenanceExpense(maintenanceExpense)
                                .miscellaneousExpense(miscellaneousExpense)

                                .totalExpenses(totalExpenses)
                                .netProfit(netProfit)

                                .completionPercentage(completionPercentage)

                                .build();
        }
}