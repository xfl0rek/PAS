package pl.pas.aplikacjarest.dto.client;

import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.ClientType;
import pl.pas.aplikacjarest.model.UserRole;

public class ClientDTO extends UserDTO {
    private ClientType clientType;

    public ClientDTO(String firstName, String lastName, String username, String email, ClientType clientType) {
        super(firstName, lastName, username, email);
        this.setUserRole(UserRole.CLIENT);
        this.clientType = clientType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public void setClientType(ClientType clientType) {
        this.clientType = clientType;
    }
}
