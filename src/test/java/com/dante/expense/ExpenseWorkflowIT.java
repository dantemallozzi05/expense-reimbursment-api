package com.dante.expense;


import org.aspectj.asm.AsmManager;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


public class ExpenseWorkflowIT {
    private MockMvc mvc;
    private ObjectMapper om;

    /**
     * Creates a SUBMITTED expense as an EMPLOYEE
     *
     * @return the created expense id
     *
     * @pre none
     *
     * @post returned id != NULL
     */
    private Long createSubmittedExpense() throws Exception {
        String body = """
        {
            "amount": 12.34,
            "currency": "USD", 
            "category": "MEALS",
            "description": "IT test"
        }
        """;

        String resp = mvc.perform(post("api/expenses")
                .header("X-User-Id", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.status").value("SUBMITTED"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return om.readTree(resp).get("id").asLong();
    }

    /**
     * Approves a SUBMITTED expense as a MANAGER
     *
     * @param expenseId id of the expense to approve
     *
     * @pre expenseId != NULL AND expense is SUBMITTED
     *
     * @post expense.status = APPROVED
     *
     */
    private void approveAsManager(Long expenseId) throws Exception {
        mvc.perform(put("/api/expenses/{id}/approve", expenseId)
                .header("X-User-Id", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expenseId))
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    /**
     * Reimburses an APPROVED expense as a Finance role
     *
     * @param expenseId the id of the expense to reimburse
     *
     * @pre expenseId != NULL AND expense is APPROVED
     *
     * @post expense.status = REIMBURSED
     */
    private void reimburseAsFinance(Long expenseId) throws Exception {
        String body = """
                { "comment": "paid out" }
                """;

        mvc.perform(put("/api/expenses/{id}/reimburse", expenseId)
                .header("X-User-Id", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expenseId))
                .andExpect(jsonPath("$.status").value("REIMBURSED"));

    }

    /**
     * End-to-end happy path test, where the employee submits, manager approves, and Finance reimburses
     *
     * @pre seeded users exist: 1 - EMPLOYEE, 2 - MANAGER, 3 - FINANCE
     *
     * @post expense transitions from SUBMITTED, to APPROVED, to REIMBURSED
     */
    @Test
    void happyPath_submitApproveReimburse() throws Exception {
        Long id = createSubmittedExpense();
        approveAsManager(id);
        reimburseAsFinance(id);
    }

    /**
     * Negative test where employee cannot approve expenses
     *
     * @pre role #1 is EMPLOYEE
     *
     * @post API returns exception
     *
     * @throws ForbiddenException
     */
    @Test
    void employeeCannotApprove() throws Exception {
        Long id = createdSubmittedExpense();

        mvc.perform(put("/api/expenses/{id}/approve", id)
                .header("X-User-Id", "1"))
                .andExpect(status().isForbidden());
    }

    /**
     * Negative test, cannot reimburse unless expense is APPROVED
     *
     * @pre expense is SUBMITTED
     *
     * @post API returns Bad Request (400)
     *
     * @throws BadRequestException
     */
    @Test
    void financeCannotReimburseSubmitted() throws Exception {
        Long id = createSubmittedExpense();

        mvc.perform(put("/api/expense/{id}/reimburse", id)
                .header("X-User-Id", "3")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"comment\":\"nope\"}"))
                .andExpect(status().isBadRequest());
    }

}
