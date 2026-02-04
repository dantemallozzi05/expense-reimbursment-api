package com.dante.expense.entity;

/**
 * Category classification for an inbound expense
 *
 * @invariant category = TRAVEL OR MEALS OR LOGIN OR SUPPLIES OR OTHER
 */
public enum ExpenseCategory {
    TRAVEL, MEALS, LOGIN, SUPPLIES, OTHER
}
