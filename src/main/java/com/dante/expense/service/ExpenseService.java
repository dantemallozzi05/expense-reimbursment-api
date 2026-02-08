package com.dante.expense.service;

import com.dante.expense.dto.CreateExpenseRequest;
import com.dante.expense.dto.ExpenseResponse;
import com.dante.expense.entity.Expense;
import com.dante.expense.entity.ExpenseAction;
import com.dante.expense.entity.User;
import com.dante.expense.exception.NotFoundException;
import com.dante.expense.repository.ExpenseActionRepository;
import com.dante.expense.repository.ExpenseRepository;
import com.dante.expense.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * A service class responsible for business logic for expense actions
 *
 * @invariant expenseRepo != NULL
 * @invariant userRepo != NULL
 * @invariant actionRepo != NULL
 */
public class ExpenseService {

    private final ExpenseRepository expenseRepo;
    private final UserRepository userRepo;
    private final ExpenseActionRepository actionRepo;

    /**
     * Constructs expense service with repositories
     *
     * @param expenseRepo expense repository
     * @param userRepo user repository
     * @param actionRepo audit repository
     *
     * @pre expenseRepo != NULL AND userRepo != NULL AND actionRepo != NULL
     *
     * @post this.expenseRepo = expenseRepo AND this.userRepo = userRepo AND this.actionRepo = actionRepo
     */
    public ExpenseService(ExpenseRepository expenseRepo, UserRepository userRepo, ExpenseActionRepository actionRepo) {
        this.expenseRepo = expenseRepo;
        this.userRepo = userRepo;
        this.actionRepo = actionRepo;
    }

    /**
     * Creates and submits an expense for the indicated user
     * Also writes a "SUBMIT" audit log
     *
     * @param userId owner user id
     * @param req request callback
     *
     * @return response representing the created expense
     *
     * @pre userId != NULL AND userId >= 0
     * @pre req != NULL
     * @pre req.amount != NULL AND req.amount > 0
     * @pre req.currency != NULL AND req.currency.length() = 3
     * @pre req.category != NULL
     * @pre req.description != NULL AND req.description.length() > 0 AND req.description.length <= 500
     * @pre req.expenseDate != NUL
     *
     * @post return != NULL
     * @post return.userId = userId
     * @post return.status = SUBMITTED
     * @post an ExpenseAction is persisted with action = "SUBMIT" for created expense
     *
     * @throws NotFoundException if user with userId doesn't exist
     */
    public ExpenseResponse createExpense(Long userId, CreateExpenseRequest req) {
        User user = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User " + userId + " not found."));

        Expense e = new Expense();

        e.setUser(user);
        e.setAmount(req.getAmount());
        e.setCurrency(req.getCurrency().toUpperCase());
        e.setCategory(req.getCategory());
        e.setDescription(req.getDescription());
        e.setExpenseDate(req.getExpenseDate());

        Expense saved = expenseRepo.save(e);

        ExpenseAction action = new ExpenseAction();

        action.setExpense(saved);
        action.setActor(user);
        action.setAction("SUBMIT");
        action.setComment(null);
        actionRepo.save(action);

        return toResponse(saved);

    }

}
