package pl.pas.aplikacjarest.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Client extends User {

    public Client(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.CLIENT);
    }

    public double applyDiscount(double price) {
        return price;
    }
}
