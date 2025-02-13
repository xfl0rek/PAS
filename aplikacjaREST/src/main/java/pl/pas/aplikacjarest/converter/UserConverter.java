package pl.pas.aplikacjarest.converter;

import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.dto.UserRegisterDTO;
import pl.pas.aplikacjarest.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public List<UserDTO> userListToUserDTOListConverter(List<User> userList) {
        return userList.stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO convertUserToDTO(User user) {
        UserDTO newUserDTO = new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getUserRole(),
                user.isActive()
        );
        newUserDTO.setId(user.getId().toString());
        return newUserDTO;
    }

    public User convertDTOToUser(UserRegisterDTO userDTO) {
        if (userDTO.getUserRole() == UserRole.ROLE_CLIENT) {
            Client client = new Client(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
            client.setActive(userDTO.getActive());
            return client;
        } else if (userDTO.getUserRole() == UserRole.ROLE_MANAGER) {
            Manager manager = new Manager(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
            manager.setActive(userDTO.getActive());
            return manager;
        } else if (userDTO.getUserRole() == UserRole.ROLE_ADMIN) {
            Admin admin = new Admin(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
            admin.setActive(userDTO.getActive());
            return admin;
        }

        return null;

    }
}
