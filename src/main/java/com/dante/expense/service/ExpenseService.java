package com.dante.expense.service;

import com.dante.expense.dto.CreateExpenseRequest;
import com.dante.expense.dto.ExpenseResponse;
import com.dante.expense.entity.Expense;
import com.dante.expense.entity.ExpenseAction;
import com.dante.expense.entity.ExpenseActionType;
import com.dante.expense.entity.User;
import com.dante.expense.exception.NotFoundException;
import com.dante.expense.exception.ForbiddenException;
import com.dante.expense.exception.BadRequestException;
import com.dante.expense.repository.ExpenseActionRepository;
import com.dante.expense.repository.ExpenseRepository;
import com.dante.expense.repository.UserRepository;
import com.dante.expense.entity.Role;
import com.dante.expense.entity.ExpenseStatus;
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
@Service
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

        logAction(saved, user, ExpenseActionType.SUBMIT, null);

        return toResponse(saved);

    }

    /**
     * Retrieves an expense via id
     *
     * @param id the id of the expense
     *
     * @return the expense response
     *
     * @pre id != NULL AND id >= 0
     *
     * @post return != NULL
     * @post return.id = id
     *
     * @throws NotFoundException if no expense exists with inputted id
     */
    public ExpenseResponse getExpense(Long id)  {
        Expense e = expenseRepo.findById(id).orElseThrow(() -> new NotFoundException("Expense " + id + " not found"));

        return toResponse(e);
    }

    /**
     * Returns the audit/action history for a given expense, ordered oldest to newest
     *
     * @param expenseId the id of the expense whose action history is requested
     *
     * @return list of ExpenseAction rows for the expense in ascending timestamp order
     *
     * @pre expenseId != NULL AND expenseId >= 0
     *
     * @post return != NULL
     * @post for each action in return: action.expense.id = expenseId
     * @post return is ordered by timestamp in ascending order
     *
     * @throws NotFoundException if the expense doesn't exist
     */
    @Transactional(readOnly = true)
    public List<ExpenseAction> getExpenseActions(Long expenseId) {
        Expense expense = expenseRepo.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense " + expenseId + " not found."));

        return actionRepo.findByExpense_IdOrderByTimestampAsc(expenseId);
    }

    /**
     * Lists all expenses belonging to the specified user
     *
     * @param userId the user's id
     *
     * @return list of expense responses ([] if none)
     *
     * @pre userId != NULL AND userId >= 0
     *
     * @post return != NULL
     * @post for all r in return, r.userId = userId
     */
    public List<ExpenseResponse> listExpensesByUser(Long userId) {
        return expenseRepo.findByUserId(userId).stream().map(this::toResponse).toList();
    }

    /**
     * @param status the status to filter expenses by
     *
     * @return a list of ExpenseResponse objs from all matching expenses
     *
     * @pre status != null
     *
     * @post result != null
     * @post for all r in result: r.status == status
     * @post result.size() == # of expense rows with Expense.status == #status
     */
    public List<ExpenseResponse> listExpenseByStatus(ExpenseStatus status) {
        return expenseRepo.findByStatus(status).stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Converts an expense obj into an ExpenseResponse dto
     *
     * @param e expense obj
     *
     * @return mapped response
     *
     * @pre e != NULL AND e.user != NULL
     *
     * @post return != NULL
     * @post return.id = e.id AND return.userId = e.user.id
     */
    private ExpenseResponse toResponse(Expense e) {
        ExpenseResponse r = new ExpenseResponse();

        r.setId(e.getId());
        r.setUserId(e.getUser().getId());
        r.setAmount(e.getAmount());
        r.setCurrency(e.getCurrency());
        r.setCategory(e.getCategory());
        r.setDescription(e.getDescription());
        r.setExpenseDate(e.getExpenseDate());
        r.setStatus(e.getStatus());
        r.setCreatedAt(e.getCreatedAt());
        r.setUpdatedAt(e.getUpdatedAt());

        return r;
    }

    /**
     * Helper method for logging each action for record
     *
     * @param expense the expense that the action was for
     * @param actor the user performing the action
     * @param type what type of action was performed
     * @param comment an optional comment
     *
     * @pre expense != NULL AND expense.id != NULL
     * @pre actor != NULL AND actor.id != NULL
     * @pre type != NULL
     *
     * @post One ExpenseAction obj is persisted
     * @post expense = #expense AND actor = #actor AND type = #type AND comment = #comment
     * @post createdAt = current timestamp
     */
    private void logAction(Expense expense, User actor, ExpenseActionType type, String comment) {
        ExpenseAction action = new ExpenseAction();

        action.setExpense(expense);
        action.setActor(actor);
        action.setActionType(type);
        action.setComment(comment);

        actionRepo.save(action);
    }

    /**
     * Approves a submitted expense and logs the action
     *
     * @param actorUserId the user's id who submitted the expense
     * @param expenseId id of the expense to approve
     *
     * @return response representing the updated expense
     *
     * @pre actorUserId != NULL AND actorUserId >= 0
     * @pre expenseId != NULL AND expenseId >= 0
     *
     * @post return != NULL
     * @post return.id = expenseId
     * @post return.status = APPROVED
     * @post an ExpenseAction is persisted with:
     *      actionType = APPROVE, actor.id = actorUserId, expense.id = expenseId
     *
     * @throws NotFoundException if actor user / expense doesn't exist
     * @throws ForbiddenException if actor isn't a MANAGER
     * @throws BadRequestException if expense.status != SUBMITTED
     */
    @Transactional
    public ExpenseResponse approveExpense(Long actorUserId, Long expenseId) {
        User actor = userRepo.findById(actorUserId).orElseThrow(() -> new NotFoundException("User " + actorUserId + " not found."));

        if (actor.getRole() != Role.MANAGER) {
            throw new ForbiddenException("Only a MANAGER can approve expenses.");
        }

        Expense expense = expenseRepo.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense " + expenseId + " not found."));

        if (expense.getStatus() != ExpenseStatus.SUBMITTED) {
            throw new BadRequestException("Only SUBMITTED expenses can be approved. Current: " + expense.getStatus());
        }

        expense.setStatus(ExpenseStatus.APPROVED);
        Expense saved = expenseRepo.save(expense);

        logAction(saved, actor, ExpenseActionType.APPROVE, null);

        return toResponse(saved);
    }

    /**
     * Rejects a submitted expense and logs a REJECT audit
     *
     * @param actorUserId id of the user performing the rejection
     * @param expenseId id of the expense to reject
     * @param reason optional comment explaining the rejection
     *
     * @return response representing the updated expense
     *
     * @pre actorUserId != NULL AND actorUserId >= 0
     * @pre expenseId != NULL AND expenseId >= 0
     *
     * @post return != NULL
     * @post return.id = expenseId
     * @post return.status = REJECTED
     * @post an ExpenseAction is persisted with:
     *      actionType = REJECT, actor.id = actorUserId, expense.id = expenseId,
     *      comment = reason
     *
     * @throws NotFoundException if actor user / expense doesn't exist
     * @throws ForbiddenException if actor isn't a MANAGER
     * @throws BadRequestException if expense.status != SUBMITTED
     */
    @Transactional
    public ExpenseResponse rejectExpense(Long actorUserId, Long expenseId, String reason) {
        User actor = userRepo.findById(actorUserId).orElseThrow(() -> new NotFoundException("User " + actorUserId + " not found."));

        if (actor.getRole() != Role.MANAGER) {
            throw new ForbiddenException("Only a MANAGER can reject expenses.");
        }

        Expense expense = expenseRepo.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense " + expenseId + " not found."));

        if (expense.getStatus() != ExpenseStatus.SUBMITTED) {
            throw new BadRequestException("Only SUBMITTED expenses can be rejected. Current: " + expense.getStatus());
        }

        expense.setStatus(ExpenseStatus.REJECTED);
        Expense saved = expenseRepo.save(expense);

        logAction(saved, actor, ExpenseActionType.REJECT, reason);

        return toResponse(saved);
    }

    /**
     * Reimburses an expense, with the option to comment on why
     *
     * @param expenseId the id of the expense to reimburse
     * @param actorUserId the id of the user performing the action
     * @param comment an optional comment
     *
     * @return The updated expense as an Expense obj
     *
     * @pre expenseId != NULL AND expenseId >= 0
     * @pre actorUserId != NULL AND actorUserId >= 0
     *
     * @post An ExpenseAction is modified to actionType = REIMBURSE, actor.id = actorUserId, expenseId = expenseId, comment = comment
     * @post return.id = expenseId
     * @post return.status = REIMBURSED
     * @post return != NULL
     *
     * @throws NotFoundException if actor user / expense doesn't exist
     * @throws ForbiddenException if actor isn't a FINANCE user
     * @throws BadRequestException if expense isn't approved
     *
     */
    public ExpenseResponse reimburseExpense(Long expenseId, Long actorUserId, String comment) {
        Expense expense = expenseRepo.findById(expenseId).orElseThrow(() -> new NotFoundException("Expense not found: " + expenseId));

        User actor = userRepo.findById(actorUserId).orElseThrow(() -> new NotFoundException("User not found: " + actorUserId));

        if (actor.getRole() != Role.FINANCE) {
            throw new ForbiddenException("Only FINANCE can reimburse expenses");
        }

        if (expense.getStatus() != ExpenseStatus.APPROVED) {
            throw new BadRequestException("Only approved expenses can be reimbursed");
        }

        expense.setStatus(ExpenseStatus.REIMBURSED);

        logAction(expense, actor, ExpenseActionType.REIMBURSE, comment);

        Expense saved = expenseRepo.save(expense);
        return toResponse(saved);
    }


}
