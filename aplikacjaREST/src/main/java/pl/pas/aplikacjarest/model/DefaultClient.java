package pl.pas.aplikacjarest.model;

public class DefaultClient extends Client {
    public DefaultClient(long personalId, String firstName, String lastName, String login, String email, String password) {
        super(personalId, firstName, lastName, login, email, password);
    }
}
