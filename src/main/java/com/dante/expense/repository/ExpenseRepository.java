package com.dante.expense.repository;

import com.dante.expense.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository interface for persisting / retrieving Expense objs
 *
 * @invariant this != NULL
 */
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    /**
     * Finds all expenses of a user given their id
     *
     * @param userId owner's user id
     *
     * @return list of expenses where expense.user.id = userId ([] if none)
     *
     * @pre userId != NULL AND userId >= 0
     *
     * @post return != NULL
     */
    List<Expense> findByUserId(Long userId);
}
