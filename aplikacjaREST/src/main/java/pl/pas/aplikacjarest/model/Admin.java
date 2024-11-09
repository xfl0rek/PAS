package pl.pas.aplikacjarest.model;

public class Admin extends User {

    public Admin(String firstName, String lastName, String username, String email, String password) {
        super(firstName, lastName, username, email, password);
        this.setUserRole(UserRole.ADMIN);
    }
}
