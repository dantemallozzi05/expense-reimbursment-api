package com.dante.expense.dto;

import com.dante.expense.entity.ExpenseCategory;
import com.dante.expense.entity.ExpenseStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Response obj returned for expense reads.
 *
 * @invariant id != NULL AND id >= 0
 * @invariant userId != NULL and userId >= 0
 * @invariant amount != NULL AND amount > 0
 * @invariant currency !- NULL AND amount > 0
 * @invariant category != NULL
 * @invariant description != NULL AND description.length() <= 500
 * @invariant expenseDate != NULL
 * @invariant status != NULL
 * @invariant createdAt != NULL
 * @invariant updatedAt != NULL
 */
public class ExpenseResponse {
    private Long id;
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private ExpenseCategory category;
    private String description;
    private LocalDate expenseDate;
    private ExpenseStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ExpenseStatus getStatus() {
        return status;
    }

    public void setStatus(ExpenseStatus status) {
        this.status = status;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpenseCategory getCategory() {
        return category;
    }

    public void setCategory(ExpenseCategory category) {
        this.category = category;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }




}
