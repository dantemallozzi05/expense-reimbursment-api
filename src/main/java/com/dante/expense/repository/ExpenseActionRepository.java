package com.dante.expense.repository;

import com.dante.expense.entity.ExpenseAction;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for persisting / retrieving ExpenseAction audit records
 *
 * @invariant this != NULL
 */
public interface ExpenseActionRepository extends JpaRepository<ExpenseAction, Long> {}
