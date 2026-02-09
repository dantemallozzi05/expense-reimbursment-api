package com.dante.expense.controller;

import com.dante.expense.dto.CreateExpenseRequest;
import com.dante.expense.dto.ExpenseResponse;
import com.dante.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for expense endpoints
 * No authentication as yet
 *
 * @invariant expenseService != NULL
 */
public class ExpenseController {
    private final ExpenseService expenseService;

    /**
     * Constructs controller
     *
     * @param expenseService service layer
     *
     * @pre expenseService != NULL
     *
     * @post this.expenseService = expenseService
     */
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    /**
     * Create & submit a new expense for given user
     *
     * @param userId the current user's id
     * @param req the validated request
     *
     * @return created expense response
     *
     * @pre userId != NULL AND userId >= 0
     * @pre req != NULL
     *
     * @post return != NULL
     * @post return.userId = userId
     */
    public ExpenseResponse create(Long userId, CreateExpenseRequest req) {
        return expenseService.createExpense(userId, req);
    }

    /**
     * Gets an expense by the id
     *
     * @param id expense id
     *
     * @return expense response
     *
     * @pre id != NULL AND id >= 0
     *
     * @post return != NULL
     * @post return.id = id
     */
    public ExpenseResponse getById(Long id) {
        return expenseService.getExpense(id);
    }

    /**
     * Lists expenses for a given user
     *
     * @param userId user id
     *
     * @return list of expense responses ([] if none)
     *
     * @pre userId != NULL AND userId >= 0
     *
     * @post return != NULL
     * @post for all r in return, r.userId = userId
     */
    public List<ExpenseResponse> listByUser(Long userId) {
        return expenseService.ListExpensesByUser(userId);
    }


}
