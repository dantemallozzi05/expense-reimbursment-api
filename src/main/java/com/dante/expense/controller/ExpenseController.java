package com.dante.expense.controller;

import com.dante.expense.dto.CreateExpenseRequest;
import com.dante.expense.dto.ExpenseResponse;
import com.dante.expense.dto.RejectExpenseRequest;
import com.dante.expense.entity.ExpenseAction;
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
    public ExpenseResponse getById(@PathVariable("id") Long id) {
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
        return expenseService.listExpensesByUser(userId);
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

    /**
     * Reimburses a previously approved expense, while logging a REIMBURSE audit
     *
     * @param id id of the expense to reimburse
     * @param actorUserId the user performing the reimbursement
     * @param body request with comment
     *
     * @return response representing the updated expense
     *
     * @pre actorUserId != NULL AND actorUserId >= 0
     * @pre id != NULL AND id >= 0
     *
     * @post return != NULL
     * @post return.id = id
     * @post return.status = REIMBURSED
     * @post an ExpenseAction is persisted with actionType = REIMBURSE, actor.id = actorUserId, expense.id = id, comment = body.comment
     *
     * @throws NotFoundException if actor user or id doesn't exist
     * @throws ForbiddenException if actor isn't a FINANCE user
     * @throws BadRequestException if expense status isn't approved
     */
    @PutMapping("/{id}/reimburse")
    public ExpenseResponse reimburse(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long actorUserId,
            @RequestBody(required = false) CommentRequest body
    ) {
        String comment = (body == null) ? null : body.comment();

        return expenseService.reimburseExpense(id, actorUserId, comment);
    }

    /**
     *
     * @param expenseId id of the expense whose actions are being retrieved
     *
     * @return list of ExpenseAction rows for an expense along with their timestamp
     *
     * @pre expenseId != NULL AND expenseId >= 0
     *
     * @post return != NULL
     * @post every actions expense.id = expenseId
     * @post return is ordered oldest first
     *
     * @throws NotFoundException if expense doesn't exist
     *
     */
    @GetMapping("/{id}/actions")
    public List<ExpenseAction> getActions(@PathVariable("id") Long expenseId) {
        return expenseService.getExpenseActions(expenseId);
    }



}
