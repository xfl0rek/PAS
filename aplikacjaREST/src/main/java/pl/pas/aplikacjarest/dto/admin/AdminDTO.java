package pl.pas.aplikacjarest.dto.admin;

import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.UserRole;

public class AdminDTO extends UserDTO {

    public AdminDTO(String firstName, String lastName, String username, String email) {
        super(firstName, lastName, username, email);
        this.setUserRole(UserRole.ADMIN);
    }
}
