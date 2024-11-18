package pl.pas.aplikacjarest.converter;

import pl.pas.aplikacjarest.dto.UserDTO;
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
        return new UserDTO(
                user.getFirstName(),
                user.getLastName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getUserRole()
        );
    }

    public User convertDTOToUser(UserDTO userDTO) {
        if (userDTO.getUserRole() == UserRole.CLIENT) {
            return new Client(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
        } else if (userDTO.getUserRole() == UserRole.MANAGER) {
            return new Manager(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
        } else if (userDTO.getUserRole() == UserRole.ADMIN) {
            return new Admin(
                    userDTO.getFirstName(),
                    userDTO.getLastName(),
                    userDTO.getUsername(),
                    userDTO.getEmail(),
                    userDTO.getPassword()
            );
        }

        return null;

    }
}
