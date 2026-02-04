package com.dante.expense.entity;

/**
 *  Role classification for users in the system
 *
 * @invariant role = EMPLOYEE OR MANAGER OR FINANCE
 */
public enum Role {
    EMPLOYEE, MANAGER, FINANCE
}
