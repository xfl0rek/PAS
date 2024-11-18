package pl.pas.aplikacjarest.exception;

public class RentAlreadyEndedException extends RuntimeException {
    public RentAlreadyEndedException(String message) {
        super(message);
    }
}
