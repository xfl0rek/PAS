package pl.pas.aplikacjamvc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pl.pas.aplikacjamvc.model.UserRole;

public class UserDTO {
    @NotNull(message = "First name cannot be null")
    @Size(min = 3, max = 30, message = "First name must be between 3 and 30 characters")
    private String firstName;
    @NotNull(message = "Last name cannot be null")
    @Size(min = 3, max = 30, message = "Last name must be between 3 and 30 characters")
    private String lastName;
    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String username;
    @NotNull(message = "Email cannot be null")
    @Size(min = 10, max = 50, message = "Email must be between 10 and 50 characters")
    @Pattern(regexp = "^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "Invalid email format")
    private String email;
    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;
    @NotNull(message = "User role cannot be null")
    private UserRole userRole;

    public UserDTO(String firstName, String lastName, String username, String email, String password, UserRole userRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    public UserDTO() {

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

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
