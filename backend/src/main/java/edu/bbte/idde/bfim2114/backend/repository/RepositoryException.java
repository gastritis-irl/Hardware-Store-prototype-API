package edu.bbte.idde.bfim2114.backend.repository;

import java.io.Serial;

public class RepositoryException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RepositoryException() {
        super();
    }

    public RepositoryException(final String message) {
        super(message);
    }

    public RepositoryException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
