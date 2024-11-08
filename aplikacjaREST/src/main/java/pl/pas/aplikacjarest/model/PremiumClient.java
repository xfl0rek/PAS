package pl.pas.aplikacjarest.model;

public class PremiumClient extends Client {

    public PremiumClient(String firstName, String lastName, String login, String email, String password) {
        super(firstName, lastName, login, email, password);
    }

    @Override
    public double applyDiscount(double price) {
        return price * 0.85;
    }
}
