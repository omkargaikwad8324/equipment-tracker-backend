package com.shivswarajya.equipmenttracker.controller;

import com.shivswarajya.equipmenttracker.dto.request.ExpenseDTO;
import com.shivswarajya.equipmenttracker.dto.response.ExpenseResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import com.shivswarajya.equipmenttracker.mapper.ExpenseMapper;
import com.shivswarajya.equipmenttracker.service.ExpenseService;
import com.shivswarajya.equipmenttracker.util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseService expenseService, ExpenseMapper expenseMapper) {
        this.expenseService = expenseService;
        this.expenseMapper = expenseMapper;
    }

    @PostMapping
    public ApiResponse<ExpenseResponseDTO> addExpense(@Valid @RequestBody ExpenseDTO dto) {
        Expense expense = expenseService.addExpense(dto);
        return new ApiResponse<>(
                true,
                "Expense added successfully",
                expenseMapper.toResponse(expense)
        );
    }

    @GetMapping
    public ApiResponse<List<ExpenseResponseDTO>> getAllExpenses() {
        List<ExpenseResponseDTO> response = expenseService.getAllExpenses()
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
        return new ApiResponse<>(
                true,
                "Expenses fetched successfully",
                response
        );
    }

    @GetMapping("/{id}")
    public ApiResponse<ExpenseResponseDTO> getExpense(@PathVariable Long id) {
        Expense expense = expenseService.getExpense(id);
        return new ApiResponse<>(
                true,
                "Expense fetched successfully",
                expenseMapper.toResponse(expense)
        );
    }

    @PutMapping("/{id}")
    public ApiResponse<ExpenseResponseDTO> updateExpense(
            @PathVariable Long id,
            @Valid @RequestBody ExpenseDTO dto) {
        Expense expense = expenseService.updateExpense(id, dto);
        return new ApiResponse<>(
                true,
                "Expense updated successfully",
                expenseMapper.toResponse(expense)
        );
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return new ApiResponse<>(
                true,
                "Expense deleted successfully",
                null
        );
    }

    @GetMapping("/equipment/{equipmentId}")
    public ApiResponse<List<ExpenseResponseDTO>> getByEquipment(@PathVariable Long equipmentId) {
        List<ExpenseResponseDTO> response = expenseService.getByEquipment(equipmentId)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
        return new ApiResponse<>(
                true,
                "Equipment expenses fetched successfully",
                response
        );
    }

    @GetMapping("/category/{category}")
    public ApiResponse<List<ExpenseResponseDTO>> getByCategory(@PathVariable ExpenseCategory category) {
        List<ExpenseResponseDTO> response = expenseService.getByCategory(category)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
        return new ApiResponse<>(
                true,
                "Expenses fetched successfully for category: " + category,
                response
        );
    }

    @GetMapping("/mode/{paymentMode}")
    public ApiResponse<List<ExpenseResponseDTO>> getByPaymentMode(@PathVariable PaymentMode paymentMode) {
        List<ExpenseResponseDTO> response = expenseService.getByPaymentMode(paymentMode)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
        return new ApiResponse<>(
                true,
                "Expenses fetched successfully for payment mode: " + paymentMode,
                response
        );
    }

    @GetMapping("/date")
    public ApiResponse<List<ExpenseResponseDTO>> getByDateRange(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {
        List<ExpenseResponseDTO> response = expenseService.getByDateRange(from, to)
                .stream()
                .map(expenseMapper::toResponse)
                .toList();
        return new ApiResponse<>(
                true,
                "Expenses fetched successfully for date range",
                response
        );
    }
}
