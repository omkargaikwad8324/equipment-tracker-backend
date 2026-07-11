
package com.shivswarajya.equipmenttracker.service.impl;

import com.shivswarajya.equipmenttracker.dto.request.ExpenseDTO;
import com.shivswarajya.equipmenttracker.entity.Equipment;
import com.shivswarajya.equipmenttracker.entity.Expense;
import com.shivswarajya.equipmenttracker.enums.ExpenseCategory;
import com.shivswarajya.equipmenttracker.enums.PaymentMode;
import com.shivswarajya.equipmenttracker.exception.BadRequestException;
import com.shivswarajya.equipmenttracker.exception.ResourceNotFoundException;
import com.shivswarajya.equipmenttracker.repository.EquipmentRepository;
import com.shivswarajya.equipmenttracker.repository.ExpenseRepository;
import com.shivswarajya.equipmenttracker.service.ExpenseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final EquipmentRepository equipmentRepository;

    public ExpenseServiceImpl(
            ExpenseRepository expenseRepository,
            EquipmentRepository equipmentRepository) {
        this.expenseRepository = expenseRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Override
    @Transactional
    public Expense addExpense(ExpenseDTO dto) {
        Objects.requireNonNull(dto, "ExpenseDTO is required");

        Equipment equipment = null;
        if (dto.getEquipmentId() != null) {
            equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Equipment not found with id : " + dto.getEquipmentId()));
        }

        Expense expense = Expense.builder()
                .expenseNo(generateExpenseNumber())
                .expenseDate(dto.getExpenseDate())
                .amount(dto.getAmount())
                .category(dto.getCategory())
                .paymentMode(dto.getPaymentMode())
                .equipment(equipment)
                .remarks(dto.getRemarks())
                .build();

        return expenseRepository.save(expense);
    }

    private String generateExpenseNumber() {

        Optional<Expense> last = expenseRepository.findTopByOrderByIdDesc();

        if (last.isEmpty()) {
            return "EXP-000001";
        }

        String lastNo = last.get().getExpenseNo();

        int number = Integer.parseInt(lastNo.substring(4));

        return String.format("EXP-%06d", number + 1);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAllWithEquipment();
    }

    @Override
    @Transactional(readOnly = true)
    public Expense getExpense(Long id) {
        Long expenseId = Objects.requireNonNull(id, "Expense ID is required");
        return expenseRepository.findByIdWithEquipment(expenseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Expense not found with id : " + expenseId));
    }

    @Override
    @Transactional
    public Expense updateExpense(Long id, ExpenseDTO dto) {
        Objects.requireNonNull(dto, "ExpenseDTO is required");
        Expense expense = getExpense(id);

        Equipment equipment = null;
        if (dto.getEquipmentId() != null) {
            equipment = equipmentRepository.findById(dto.getEquipmentId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Equipment not found with id : " + dto.getEquipmentId()));
        }

        expense.setExpenseDate(dto.getExpenseDate());
        expense.setAmount(dto.getAmount());
        expense.setCategory(dto.getCategory());
        expense.setPaymentMode(dto.getPaymentMode());
        expense.setEquipment(equipment);
        expense.setRemarks(dto.getRemarks());

        return expenseRepository.save(expense);
    }

    @Override
    @Transactional
    public void deleteExpense(Long id) {
        Expense expense = getExpense(id);
        expenseRepository.delete(expense);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getByEquipment(Long equipmentId) {
        Long eqId = Objects.requireNonNull(equipmentId, "Equipment ID is required");
        if (!equipmentRepository.existsById(eqId)) {
            throw new ResourceNotFoundException("Equipment not found with id : " + eqId);
        }
        return expenseRepository.findByEquipmentIdWithEquipment(equipmentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getByCategory(ExpenseCategory category) {
        Objects.requireNonNull(category, "Category is required");
        return expenseRepository.findByCategoryWithEquipment(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getByPaymentMode(PaymentMode paymentMode) {
        Objects.requireNonNull(paymentMode, "Payment mode is required");
        return expenseRepository.findByPaymentModeWithEquipment(paymentMode);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Expense> getByDateRange(LocalDate from, LocalDate to) {
        Objects.requireNonNull(from, "From date is required");
        Objects.requireNonNull(to, "To date is required");
        if (to.isBefore(from)) {
            throw new BadRequestException("To date must be after or equal to From date");
        }
        return expenseRepository.findByExpenseDateBetweenWithEquipment(from, to);
    }
}
