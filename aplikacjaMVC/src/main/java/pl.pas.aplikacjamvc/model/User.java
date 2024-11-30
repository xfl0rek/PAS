package pl.pas.aplikacjamvc.model;

import jakarta.validation.constraints.Size;
import org.bson.types.ObjectId;

public class User {
    private ObjectId id;
    @Size(min = 3, max = 30)
    private String firstName;
    @Size(min = 3, max = 30)
    private String lastName;
    @Size(min = 5, max = 30)
    private String username;
    @Size(min = 10, max = 50)
    private String email;
    @Size(min = 8, max = 50)
    private String password;
    private boolean isActive = false;

    private UserRole userRole;

    public User(String firstName, String lastName, String username, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
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


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
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