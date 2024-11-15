package pl.pas.aplikacjarest.dto.client;

import jakarta.validation.constraints.Size;
import pl.pas.aplikacjarest.model.ClientType;

public class ClientCreateDTO {
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

    private ClientType clientType;

    public ClientCreateDTO(String firstName, String lastName, String username,
                           String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.clientType = ClientType.Default;
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

    public ClientType getClientType() {
        return clientType;
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

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
