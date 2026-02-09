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
@Entity
@Table(name = "expense_actions")
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
     *
     * @pre none
     *
     * @post timestamp != NULL
     */
    @PrePersist
    void onCreate() {
        timestamp = OffsetDateTime.now();
    }

    /**
     * @return action id
     *
     * @pre none
     *
     * @post getId = id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id")
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
     * @return user performing the action
     *
     * @pre none
     *
     * @post getActor = actor
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    public User getActor() {
        return actor;
    }

    /**
     * Sets the user performing the action
     *
     * @param actor user who performed action
     *
     * @pre actor != NULL
     *
     * @post this.actor = actor
     */
    public void setActor(User actor) {
        this.actor = actor;
    }

    /**
     * @return type of action as a str
     *
     * @pre none
     *
     * @post getAction = action
     */
    @Column(nullable = false)
    public String getAction() {
        return action;
    }

    /**
     * Sets the type of action performed
     *
     * @param action action type as str
     *
     * @pre action != NULL AND action.length() > 0
     *
     * @post this.action = action
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @return comment of action
     *
     * @pre none
     *
     * @post getComment = comment
     */
    @Column(length = 500)
    public String getComment() {
        return comment;
    }

    /**
     * Sets an optional comment
     *
     * @param comment the comment as a str
     *
     * @pre none
     *
     * @post this.comment = comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * @return timestamp of action
     *
     * @pre none
     *
     * @post getTimestamp = timestamp
     */
    @Column(nullable = false)
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the timestamp of an action
     *
     * @param timestamp the timestamp
     *
     * @pre timestamp != NULL
     *
     * @post this.timestamp = timestamp
     */
    public void setTimestamp(OffsetDateTime timestamp) {
        this.timestamp = timestamp;
    }




}
