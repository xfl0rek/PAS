package pl.pas.aplikacjarest.exception;

public class JwsException extends  RuntimeException {
    public JwsException(String message) {
        super(message);
    }
}
