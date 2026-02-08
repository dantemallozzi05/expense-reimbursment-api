package com.dante.expense.exception;

import java.time.OffsetDateTime;

/**
 * Standard API error exception
 *
 * @invariant timestamp != NULL
 * @invariant status > 0
 * @invariant error = != NULL AND error.length() > 0
 * @invariant message != NULL AND message.length() > 0
 * @invariant path != NULL AND path.length() > 0
 */
public class ApiError {

    private OffsetDateTime timestamp = OffsetDateTime.now();
    private int status;
    private String error;
    private String message;
    private String path;

    public OffsetDateTime getTimestamp() { return timestamp; }

    public void setTimestamp(OffsetDateTime timestamp) { this.timestamp = timestamp; }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
