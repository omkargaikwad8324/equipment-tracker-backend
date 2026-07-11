package com.shivswarajya.equipmenttracker.dto.request;

import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseDTO {

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;

    @NotNull(message = "Expense category is required")
    private ExpenseCategory category;

    @NotNull(message = "Payment mode is required")
    private PaymentMode paymentMode;

    private Long equipmentId;

    private String remarks;
}
