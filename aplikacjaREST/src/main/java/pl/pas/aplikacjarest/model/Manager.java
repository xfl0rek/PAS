package pl.pas.aplikacjarest.model;

public class Manager extends User {

    public Manager(String firstName, String lastName, String login, String email, String password) {
        super(firstName, lastName, login, email, password);
        this.setUserRole(UserRole.MANAGER);
    }
}
