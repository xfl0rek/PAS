package pl.pas.aplikacjarest.exception;

public class RentTransactionException extends RuntimeException {
    public RentTransactionException(String message) {
        super(message);
    }

    public RentTransactionException(String message, Throwable cause) {
        super(message, cause);
    }
}
