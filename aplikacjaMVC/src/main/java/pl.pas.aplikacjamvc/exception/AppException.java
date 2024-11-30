package pl.pas.aplikacjamvc.exception;

public class AppException extends Exception {
    private int statusCode;

    public AppException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
