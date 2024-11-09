package pl.pas.aplikacjarest.model;

public class PremiumClient extends Client {

    public PremiumClient(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
    }

    @Override
    public double applyDiscount(double price) {
        return price * 0.85;
    }
}
