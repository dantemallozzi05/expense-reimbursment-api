package com.dante.expense.exception;


/**
 * An exception indicating that a requested piece of data was not found
 *
 * @invariant message != NULL AND message.length() > 0
 */
public class NotFoundException extends RuntimeException {

    /**
     * Constructs NotFoundException with message
     *
     * @param message comment of missing data
     *
     * @pre message != NULL AND message.length() > 0
     *
     * @post getMessage() = message
     */
    public NotFoundException(String message) {
        super(message);
    }
}
