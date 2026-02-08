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

    /**
     * sets the owning user for this expense
     *
     * @param user the user with the expense
     *
     * @pre user != NULL
     *
     * @post this.user = user
     */
    public void setUser(User user) { this.user = user; }

    /**
     * Gets the cost of the expense
     *
     * @return the expense amount
     *
     * @pre none
     *
     * @post getAmount = amount
     */
    public BigDecimal getAmount() { return amount; }

    /**
     * Sets the expense amount
     *
     * @param amount amount to set the expense as
     *
     * @pre amount != NULL AND amount > 0
     *
     * @post this.amount = amount
     */
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    /**
     * Retrieve the currency type
     *
     * @return 3 letter currency code
     *
     * @pre none
     *
     * @post getCurrency = currency
     */
    public String getCurrency() { return currency; }

    /**
     * Set the currency type
     *
     * @param currency the 3 letter code to set
     *
     * @pre currency != NULL AND currency.length() = 3
     *
     * @post this.currency = currency
     */
    public void setCurrency(String currency) { this.currency = currency; }

    /**
     * Get category of expense
     *
     * @return category of expense
     *
     * @pre none
     *
     * @post getCategory = category
     */
    public ExpenseCategory getCategory() { return category; }

    /**
     * set the category of the expense
     *
     * @param category the category to set
     *
     * @pre category != NULL
     *
     * @post this.category = category
     */
    public void setCategory(ExpenseCategory category) { this.category = category; }

}
