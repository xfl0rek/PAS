package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.converter.UserConverter;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.exception.UserNotFoundException;
import pl.pas.aplikacjarest.exception.UsernameAlreadyInUseException;
import pl.pas.aplikacjarest.exception.WrongPasswordException;
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
        if (user == null)
            throw new UserNotFoundException("User not found");

        if (!user.getPassword().equals(loginDTO.getPassword()))
            throw new WrongPasswordException("Invalid password");

        return userConverter.convertUserToDTO(user);
    }

    public UserDTO registerUser(UserDTO userDTO) {
        User checkUser = userRepository.findByUsername(userDTO.getUsername());
        if (checkUser != null)
            throw new UsernameAlreadyInUseException("User " + userDTO.getUsername() +  " already exists");
        User user = userConverter.convertDTOToUser(userDTO);
        userRepository.save(user);
        return userConverter.convertUserToDTO(user);
    }

    public UserDTO getUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UserNotFoundException("User not found");
        return userConverter.convertUserToDTO(user);
    }

    public List<UserDTO> getUsersByPartialUsername(String partialUsername) {
        List<User> users = userRepository.findUsersByPartialUsername(partialUsername);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public void activateAccount(ObjectId userID) {
        User user = userRepository.findByID(userID);
        if (user == null)
            throw new UserNotFoundException("User not found");
        userRepository.activateUser(userID);
    }

    public void deactivateAccount(ObjectId userID) {
        User user = userRepository.findByID(userID);
        if (user == null)
            throw new UserNotFoundException("User not found");
        userRepository.deactivateUser(userID);
    }

    public void changeUserRole(ObjectId userID, UserRole userRole) {
        User user = userRepository.findByID(userID);
        if (user == null)
            throw new UserNotFoundException("User not found");
        user.setUserRole(userRole);
        userRepository.update(user);
    }

    public List<UserDTO> findAllByRole(UserRole userRole) {
        List<User> users = userRepository.findAllByRole(userRole);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return userConverter.userListToUserDTOListConverter(users);

    }

    public UserDTO getUserByID(ObjectId userID) {
        User user = userRepository.findByID(userID);
        if (user == null)
            throw new UserNotFoundException("User not found");
        return userConverter.convertUserToDTO(user);
    }
}
