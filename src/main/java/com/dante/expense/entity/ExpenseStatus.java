package com.dante.expense.entity;

/**
 * Stored status for an inbound expense request
 *
 * @invariant status = SUBMITTED OR APPROVED OR REJECTED OR REIMBURSED
 */
public enum ExpenseStatus {
    SUBMITTED, APPROVED, REJECTED, REIMBURSED
}
