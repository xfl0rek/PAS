package pl.pas.aplikacjamvc.model;

public class Client extends User {

    public Client (String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.CLIENT);
        setActive(true);
    }

    public Client() {
    }
}
