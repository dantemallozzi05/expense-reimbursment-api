package com.dante.expense.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Centralized exception handler that converts exceptions into consistent JSON error responses
 *
 * @invariant this != NULL
 */
public class GlobalExceptionHandler {

    /**
     * Handles NotFoundException by returning HTTP 404 with an ApiError body
     *
     * @param ex the thrown NotFoundException
     * @param req http request
     *
     * @return ResponseEntity<ApiError> with status 404
     *
     * @pre ex != NULL AND req != NULL
     *
     * @post return != NULL
     * @post return.status = 404
     * @post return.body.status = 404
     * @post return.body.path = req.getRequestURI()
     */
    public ResponseEntity<ApiError> notFound(NotFoundException ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.setStatus(NOT_FOUND.value());
        err.setError("Not Found");
        err.setMessage(ex.getMessage());
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(NOT_FOUND).body(err);
    }

    /**
     * Handles validation errors by returning HTTP 400 with an Api Error
     *
     * @param ex validation exception
     * @param req http request
     *
     * @return ResponseEntity<ApiError> with status 400
     *
     * @pre ex != NULL AND req != NULL
     *
     * @post return != NULL
     * @post return.status = 400
     * @post return.body.status = 400
     * @post return.body.path = req.getRequestURI()
     */
    public ResponseEntity<ApiError> badValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ApiError err = new ApiError();
        err.setStatus(BAD_REQUEST.value());
        err.setError("Bad Request");
        err.setMessage(
                ex.getBindingResult().getFieldErrors().stream()
                        .findFirst()
                        .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                        .orElse("Validation failed")
        );
        err.setPath(req.getRequestURI());
        return ResponseEntity.status(BAD_REQUEST).body(err);
    }
}
