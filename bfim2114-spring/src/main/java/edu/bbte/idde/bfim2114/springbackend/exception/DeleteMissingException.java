package edu.bbte.idde.bfim2114.springbackend.exception;

import java.io.Serial;

public class DeleteMissingException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    public DeleteMissingException(String message) {
        super(message);
    }

    public DeleteMissingException(String message, Throwable cause) {
        super(message, cause);
    }
}
