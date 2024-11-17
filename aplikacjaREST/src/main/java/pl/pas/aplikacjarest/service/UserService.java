package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.converter.UserConverter;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.model.*;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    UserRepository userRepository;
    UserConverter userConverter = new UserConverter();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {

            return new UserDTO(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getUserRole()
            );
        }
        return null;
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User checkUser = userRepository.findByUsername(userDTO.getUsername());
        if (checkUser == null) {
            User user = userConverter.convertDTOToUser(userDTO);
            userRepository.save(user);
            return userConverter.convertUserToDTO(user);
        }
        return null;
    }

    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return new UserDTO(
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getUserRole());
            }
        return null;
    }

    public List<UserDTO> getUsersByPartialUsername(String partialUsername) {
        List<User> users = userRepository.findUsersByPartialUsername(partialUsername);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public void activateAccount(String username) {
        userRepository.activateUser(username);
    }

    public void deactivateAccount(String username) {
        userRepository.deactivateUser(username);
    }

    public void changeUserRole(String username, UserRole userRole) {
        User user = userRepository.findByUsername(username);
        user.setUserRole(userRole);
        userRepository.update(user);
    }

    public List<UserDTO> findAll(UserRole userRole) {
        List<User> users = userRepository.findAll(userRole);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public UserDTO getUserByID(ObjectId userID) {
        User user = userRepository.findByID(userID);
        return userConverter.convertUserToDTO(user);
    }
}
