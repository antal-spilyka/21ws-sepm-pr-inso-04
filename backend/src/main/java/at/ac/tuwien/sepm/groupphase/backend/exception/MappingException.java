package at.ac.tuwien.sepm.groupphase.backend.exception;

public class MappingException extends RuntimeException {
    public MappingException(String message) {
        super(message);
    }

    public MappingException(Throwable cause) {
        super(cause);
    }

    public MappingException(String message, Throwable cause) {
        super(message, cause);
    }
}
