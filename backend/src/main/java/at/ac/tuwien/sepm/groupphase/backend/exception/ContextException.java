package at.ac.tuwien.sepm.groupphase.backend.exception;

public class ContextException extends RuntimeException {
    public ContextException() {
    }

    public ContextException(String message) {
        super(message);
    }

    public ContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContextException(Exception e) {
        super(e);
    }
}
