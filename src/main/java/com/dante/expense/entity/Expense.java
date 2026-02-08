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
        OffsetDateTime now = OffsetDateTime.now();

        createdAt = now;
        updatedAt = now;

        if (status == null) status = ExpenseStatus.SUBMITTED;
        if (currency == null) currency = "USD";
    }

    /**
     * updates updatedAt timestamp on update
     *
     * @pre none
     *
     * @post updatedAt is updated to current time
     */
    void onUpdate() {
        updatedAt = OffsetDateTime.now();
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
     * @param currency the 3-letter code to set
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

    /**
     * Retrieve description of expense
     *
     * @return description string
     *
     * @pre none
     *
     * @post getDescription = description
     */
    public String getDescription() { return description; }

    /**
     * Set description of expense
     *
     * @param description the string to describe the expense
     *
     * @pre description != NULL AND description.length() > 0 AND description.length() <= 500
     *
     * @post this.description = description
     */
    public void setDescription(String description) { this.description = description; }

    /**
     * Retrieve the date of the expense
     *
     * @return the date the expense occurred
     *
     * @pre none
     *
     * @post getExpenseDate = expenseDate
     */
    public LocalDate getExpenseDate() { return expenseDate; }

    /**
     * Set the date of the expense
     *
     * @param expenseDate the date
     *
     * @pre expenseDate != NULL
     *
     * @post this.expenseDate = expenseDate
     */
    public void setExpenseDate(LocalDate expenseDate) { this.expenseDate = expenseDate; }

    /**
     * Retrieves status of expense
     *
     * @return expense status
     *
     * @pre none
     *
     * @post getStatus = status
     */
    public ExpenseStatus getStatus() { return status; }

    /**
     * Sets the status of the expense
     *
     * @param status status to set
     *
     * @pre status != NULL
     *
     * @post this.status = status
     */
    public void setStatus(ExpenseStatus status) { this.status = status; }

    /**
     * Get the timestamp of creation
     *
     * @return creation time
     *
     * @pre none
     *
     * @post getCreatedAt = createdAt
     */
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Set the timestamp of creation
     *
     * @param createdAt the timestamp
     *
     * @pre createdAt != NULL
     *
     * @post this.createdAt = createdAt
     */
    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Retrieves the timestamp of most recent update
     *
     * @return timestamp of update
     *
     * @pre none
     *
     * @post getUpdatedAt = updatedAt
     */
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Sets upDatedAt, usually set by controller
     *
     * @param updatedAt the timestamp
     *
     * @pre updatedAt != NULL
     *
     * @post this.updatedAt = updatedAt
     */
    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


}
