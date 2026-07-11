package com.shivswarajya.equipmenttracker.dto.response;

import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ExpenseResponseDTO {

    private Long id;
    private LocalDate expenseDate;
    private Double amount;
    private ExpenseCategory category;
    private PaymentMode paymentMode;
    private Long equipmentId;
    private String equipmentName;
    private String registrationNumber;
    private String remarks;
    private String expenseNo;
}
