package com.dante.expense.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;

/**
 * Class representing a single audit log entry for an expense.
 * Stores who performed any action, what the action was, when it occurred,
 * and a comment if desired
 *
 * @invariant id >= 0
 * @invariant expense != NULL
 * @invariant actor != NULL
 * @invariant action != NULL AND action.length() > 0
 * @invariant timestamp != NULL
 */
public class ExpenseAction {
    private Long id;
    private Expense expense;
    private User actor;
    private String action;
    private String comment;
    private OffsetDateTime timestamp;

    /**
     * Default constructor
     *
     * @pre none
     *
     * @post an ExpenseAction obj is created
     */
    public ExpenseAction() {}

    /**
     * autopopulates the timestamp on insert
     */
    void onCreate() {

    }

    /**
     * @return action id
     *
     * @pre none
     *
     * @post getId = id
     */
    public Long getId() { return id; }

    /**
     * Sets the id, automated
     *
     * @param id id value
     *
     * @pre id != NULL AND id >= 0
     *
     * @post this.id = id
     */
    public void setId(Long id) { this.id = id; }

    /**
     *
     * @return the expense the action is for
     *
     * @pre none
     *
     * @post getExpense = expense
     */
    public Expense getExpense() {
        return expense;
    }

    /**
     * Sets the expense the action references
     *
     * @param expense expense to reference
     *
     * @pre expense != NULL
     *
     * @post this.expense = expense
     */
    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    /**
     *
     * @return
     */
    public User getActor() {
        return actor;
    }

    /**
     *
     * @param actor
     */
    public void setActor(User actor) {
        this.actor = actor;
    }

    /**
     *
     * @return
     */
    public String getAction() {
        return action;
    }

    /**
     *
     * @param action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     *
     * @return
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     */
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     */
    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }




}
