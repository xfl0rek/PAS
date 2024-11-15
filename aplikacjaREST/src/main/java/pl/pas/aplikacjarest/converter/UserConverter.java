package pl.pas.aplikacjarest.converter;

import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.dto.admin.AdminDTO;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerDTO;
import pl.pas.aplikacjarest.model.Admin;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Manager;
import pl.pas.aplikacjarest.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserConverter {
    public List<UserDTO> userListToUserDTOListConverter(List<User> userList) {
        return userList.stream()
                .map(this::convertUserToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO userToUserDTOConverter(User user) {
        return convertUserToDTO(user);
    }

    private UserDTO convertUserToDTO(User user) {
        if (user instanceof Client client) {
            return new ClientDTO(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getUsername(),
                    client.getEmail(),
                    client.getType()
            );
        } else if (user instanceof Manager manager) {
            return new ManagerDTO(
                    manager.getFirstName(),
                    manager.getLastName(),
                    manager.getUsername(),
                    manager.getEmail()
            );
        } else if (user instanceof Admin admin) {
            return new AdminDTO(
                    admin.getFirstName(),
                    admin.getLastName(),
                    admin.getUsername(),
                    admin.getEmail()
            );
        }
        return null;
    }
//TODO zrobic
//    public User userDTOToUser(UserDTO userDTO) {
//        if (userDTO instanceof ClientDTO) {
//            return new Client(
//                    userDTO.getFirstName(),
//                    userDTO.getLastName(),
//                    userDTO.getUsername(),
//                    userDTO.getEmail(),
//                    userDTO.
//                    ((ClientDTO) userDTO).getClientType()
//            )
//        }
//    }
}
