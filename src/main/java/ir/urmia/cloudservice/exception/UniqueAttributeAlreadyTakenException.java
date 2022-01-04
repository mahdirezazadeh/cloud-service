package ir.urmia.cloudservice.exception;

public class UniqueAttributeAlreadyTakenException extends RuntimeException {
    public UniqueAttributeAlreadyTakenException(String message) {
        super(message);
    }

    public UniqueAttributeAlreadyTakenException(String message, Throwable cause) {
        super(message, cause);
    }
}
