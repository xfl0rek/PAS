package pl.pas.aplikacjarest.exception;

public class RoomIsAlreadyRentedException extends RuntimeException {
    public RoomIsAlreadyRentedException(String message) {
        super(message);
    }
}
