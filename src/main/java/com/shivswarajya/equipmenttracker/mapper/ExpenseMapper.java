package com.shivswarajya.equipmenttracker.mapper;

import com.shivswarajya.equipmenttracker.dto.response.ExpenseResponseDTO;
import com.shivswarajya.equipmenttracker.entity.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseResponseDTO toResponse(Expense expense) {
        if (expense == null) {
            return null;
        }

        ExpenseResponseDTO.ExpenseResponseDTOBuilder builder = ExpenseResponseDTO.builder()
                .id(expense.getId())
                .expenseDate(expense.getExpenseDate())
                .amount(expense.getAmount())
                .category(expense.getCategory())
                .paymentMode(expense.getPaymentMode())
                .remarks(expense.getRemarks())
                .expenseNo(expense.getExpenseNo());

        if (expense.getEquipment() != null) {
            builder.equipmentId(expense.getEquipment().getId())
                   .equipmentName(expense.getEquipment().getEquipmentName())
                   .registrationNumber(expense.getEquipment().getRegistrationNumber());
                   
        }

        return builder.build();
    }
}
