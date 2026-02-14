package com.dante.expense.controller;

import com.dante.expense.dto.CreateExpenseRequest;
import com.dante.expense.dto.ExpenseResponse;
import com.dante.expense.dto.RejectExpenseRequest;
import com.dante.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import com.dante.expense.dto.CommentRequest;

import java.util.List;

/**
 * REST controller for expense endpoints
 * No authentication as yet
 *
 * @invariant expenseService != NULL
 */
@RestController
@RequestMapping("/api/expenses")
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
    @PostMapping
    public ExpenseResponse create(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody CreateExpenseRequest req
    ) {
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
    @GetMapping("/{id}")
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
    @GetMapping
    public List<ExpenseResponse> listByUser(@RequestParam("userId") Long userId) {
        return expenseService.ListExpensesByUser(userId);
    }

    @PutMapping("/{id}/approve")
    public ExpenseResponse approveExpense(
            @RequestHeader("X-User-Id") Long actorUserId,
            @PathVariable("id") Long expenseId
    ) {
        return expenseService.approveExpense(actorUserId, expenseId);
    }

    @PutMapping("/{id}/reject")
    public ExpenseResponse rejectExpense(
            @RequestHeader("X-User-Id") Long actorUserId,
            @PathVariable("id") Long expenseId,
            @RequestBody RejectExpenseRequest req
    ) {
        String reason = (req == null) ? null : req.getReason();
        return expenseService.rejectExpense(actorUserId, expenseId, reason);
    }

    @PutMapping("/{id}/reimburse")
    public ExpenseResponse reimburse(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long actorUserId,
            @RequestBody(required = false) CommentRequest body
    ) {
        String comment = (body == null) ? null : body.comment();

        return expenseService.reimburseExpense(id, actorUserId, comment);
    }



}
