package pl.pas.aplikacjarest.exception;

public class RentNotEndedException extends RuntimeException {
    public RentNotEndedException(String message) {
        super(message);
    }
}
