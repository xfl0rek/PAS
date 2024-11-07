package pl.pas.aplikacjarest.model;

public class Admin extends User {

    public Admin(long personalId, String firstName, String lastName, String login, String email, String password) {
        super(personalId, firstName, lastName, login, email, password);
        this.setUserRole(UserRole.ADMIN);
    }
}
