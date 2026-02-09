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
 * @invariant actionType != NULL
 * @invariant timestamp != NULL
 */
@Entity
@Table(name = "expense_actions")
public class ExpenseAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "expense_id")
    private Expense expense;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id", nullable = false)
    private User actor;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private ExpenseActionType actionType;

    @Column(length = 500)
    private String comment;

    @Column(nullable = false)
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
     * @return user performing the action
     *
     * @pre none
     *
     * @post getActor = actor
     */
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
     * @post getActionType = actionType
     */
    public ExpenseActionType getActionType() {
        return actionType;
    }

    /**
     * Sets the type of action performed
     *
     * @param actionType action type as enum
     *
     * @pre actionType != NULL
     *
     * @post this.actionType = actionType
     */
    public void setActionType(ExpenseActionType actionType) {
        this.actionType = actionType;
    }

    /**
     * @return comment of action
     *
     * @pre none
     *
     * @post getComment = comment
     */
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
