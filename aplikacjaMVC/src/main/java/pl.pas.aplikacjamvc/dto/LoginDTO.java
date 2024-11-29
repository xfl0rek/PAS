package pl.pas.aplikacjamvc.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginDTO {
    @NotNull(message = "Username cannot be null")
    @Size(min = 5, max = 30, message = "Username must be between 5 and 30 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
