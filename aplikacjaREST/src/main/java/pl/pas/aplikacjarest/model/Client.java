package pl.pas.aplikacjarest.model;

public class Client extends User {

    public Client(String firstName, String lastName, String login, String email, String password) {
        super(firstName, lastName, login, email, password);
        this.setUserRole(UserRole.CLIENT);
    }

    public double applyDiscount(double price) {
        return price;
    }
}
