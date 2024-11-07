package pl.pas.aplikacjarest.model;

public class PremiumClient extends Client {

    public PremiumClient(long personalId, String firstName, String lastName, String login, String email, String password) {
        super(personalId, firstName, lastName, login, email, password);
    }

    @Override
    public double applyDiscount(double price) {
        return price * 0.85;
    }
}
