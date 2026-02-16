package com.dante.expense.repository;

import com.dante.expense.entity.ExpenseAction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for persisting / retrieving ExpenseAction audit records
 *
 * @invariant this != NULL
 */
public interface ExpenseActionRepository extends JpaRepository<ExpenseAction, Long> {

    /**
     *
     * @param expenseId id of the expense whos actions are requested
     *
     * @return list of actions for that expense oldest first
     *
     * @pre expenseId != NULL AND expenseId >= 0
     *
     * @post return != NULL
     * @post for each action in return, action expense.id = expenseId
     * @post return is ordered by oldest first
     */
    List<ExpenseAction> findByExpenseIdOrdered(Long expenseId);
}
