package pl.pas.aplikacjarest.dto.manager;

import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.UserRole;

public class ManagerDTO extends UserDTO {
    public ManagerDTO(String firstName, String lastName, String username, String email) {
        super(firstName, lastName, username, email);
        this.setUserRole(UserRole.MANAGER);
    }
}
