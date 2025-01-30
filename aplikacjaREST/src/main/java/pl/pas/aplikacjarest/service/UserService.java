package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.converter.UserConverter;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.exception.UserNotFoundException;
import pl.pas.aplikacjarest.exception.UsernameAlreadyInUseException;
import pl.pas.aplikacjarest.exception.WrongPasswordException;
import pl.pas.aplikacjarest.model.*;
import pl.pas.aplikacjarest.repository.UserRepository;
import pl.pas.aplikacjarest.security.JwtBlackList;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
    UserRepository userRepository;
    UserConverter userConverter = new UserConverter();
    PasswordEncoder passwordEncoder;
    JwtBlackList jwtBlackList;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtBlackList jwtBlackList) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtBlackList = jwtBlackList;
    }

    public UserDTO login(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user == null)
            throw new UserNotFoundException("User not found");

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            throw new WrongPasswordException("Invalid password");

        return userConverter.convertUserToDTO(user);
    }

    public UserDTO registerUser(UserRegisterDTO userDTO) {
        User user2 = userRepository.findByUsername(userDTO.getUsername());
        if (user2 != null) {
            throw new UsernameAlreadyInUseException("Username is already in use");
        }
        User user = userConverter.convertDTOToUser(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
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

    public void updateUser(ObjectId userID, UserDTO userDTO) {
        User user = userRepository.findByID(userID);
        if (user == null)
            throw new UserNotFoundException("User not found");
        user.setUsername(userDTO.getUsername());
//        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setUserRole(userDTO.getUserRole());
        userRepository.update(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return new UserPrincipal(user);
    }

    public void changePassword(ObjectId userId, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findByID(userId);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.update(user);
    }

    public void logout(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);
            jwtBlackList.blacklistToken(jwtToken);
        }
    }
}
