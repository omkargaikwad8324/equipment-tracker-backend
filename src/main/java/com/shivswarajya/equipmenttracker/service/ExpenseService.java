package com.shivswarajya.equipmenttracker.service;

import com.shivswarajya.equipmenttracker.dto.request.ExpenseDTO;
import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;

import java.time.LocalDate;
import java.util.List;

public interface ExpenseService {

    Expense addExpense(ExpenseDTO dto);

    List<Expense> getAllExpenses();

    Expense getExpense(Long id);

    Expense updateExpense(Long id, ExpenseDTO dto);

    void deleteExpense(Long id);

    List<Expense> getByEquipment(Long equipmentId);

    List<Expense> getByCategory(ExpenseCategory category);

    List<Expense> getByPaymentMode(PaymentMode paymentMode);

    List<Expense> getByDateRange(LocalDate from, LocalDate to);

    
    
}
