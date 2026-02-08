package com.dante.expense.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * Representing any expense submitted by a user for reimbursement given their id and user obj.
 *
 * @invariant id >= 0
 * @invariant user != NULL
 * @invariant amount != NULL AND amount > 0
 * @invariant currency != NULL AND currency.length() = 3
 * @invariant category != NULL
 * @invariant description != NULL AND description.length() > 0 AND description.length <= 500
 * @invariant expenseDate != NULL
 * @invariant status != NULL
 * @invariant createdAt != NULL
 * @invariant updatedAt != NULL
 */
public class Expense {
    private Long id;
    private User user;
    private BigDecimal amount;
    private String currency;
    private ExpenseCategory category;
    private String description;
    private LocalDate expenseDate;
    private ExpenseStatus status;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    /**
     * Default constructor
     *
     * @pre none
     *
     * @post Expense obj created
     */
    public Expense() {}

    /**
     * Sets timestamps, defaults to initial persist
     *
     * @pre none
     *
     * @post createdAt != NULL AND updatedAt != NULL
     * @post status = SUBMITTED IFF status was NULL before persist
     * @post currency = "USD" IFF currency was NULL before persist
     */
    void onCreate() {

    }

    /**
     * updates updatedAt timestamp on update
     *
     * @pre none
     *
     * @post updatedAt is updated to current time
     */
    void onUpdate() {

    }

    /**
     * @return the expense id
     *
     * @pre none
     *
     * @post getId = id
     */
    public Long getId() { return id; }

    /**
     * Sets expense id
     *
     * @param id id to set
     *
     * @pre id != NULL AND id >= 0
     *
     * @post this.id = id
     */
    public void setId(Long id) { this.id = id; }


    /**
     * Retrieves the current user
     *
     * @return the user who owns this expense
     *
     * @pre none
     *
     * @post getUser = user
     */
    public User getUser() { return user; }


}
