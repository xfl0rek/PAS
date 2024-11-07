package pl.pas.aplikacjarest.model;

public class User {
    private long personalId;
    private String firstName;
    private String lastName;
    private String login;
    private String email;
    private String password;
    private boolean isActive = false;

    private UserRole userRole;

    public User(long personalId, String firstName, String lastName, String login, String email, String password) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
    }

    public long getPersonalId() {
        return personalId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setPersonalId(long personalId) {
        this.personalId = personalId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}