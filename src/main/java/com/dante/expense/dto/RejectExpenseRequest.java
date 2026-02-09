package com.dante.expense.dto;

/**
 * Request body for rejecting an expense
 */
public class RejectExpenseRequest {
    private String reason;

    public RejectExpenseRequest() {}

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}
