package pl.pas.aplikacjarest.model;

public class Client extends User {

    public Client(long personalId, String firstName, String lastName, String login, String email, String password) {
        super(personalId, firstName, lastName, login, email, password);
        this.setUserRole(UserRole.CLIENT);
    }

    public double applyDiscount(double price) {
        return price;
    }
}
