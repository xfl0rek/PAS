package pl.pas.aplikacjarest.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.converter.UserConverter;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.dto.admin.AdminCreateDTO;
import pl.pas.aplikacjarest.dto.admin.AdminDTO;
import pl.pas.aplikacjarest.dto.client.ClientCreateDTO;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerCreateDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerDTO;
import pl.pas.aplikacjarest.model.*;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserRepository userRepository;
    UserConverter userConverter = new UserConverter();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ClientDTO loginClient(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            if (user instanceof Client client) {
                return new ClientDTO(
                        client.getFirstName(),
                        client.getLastName(),
                        client.getUsername(),
                        client.getEmail(),
                        client.getType()
                );
            }
        }
        System.out.println("User not found");
        return null;
    }

    public ManagerDTO loginManager(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            if (user instanceof Manager manager) {
                return new ManagerDTO(
                        manager.getFirstName(),
                        manager.getLastName(),
                        manager.getUsername(),
                        manager.getEmail()
                );
            }
        }
        return null;
    }

    public AdminDTO loginAdmin(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            if (user instanceof Admin admin) {
                return new AdminDTO(
                        admin.getFirstName(),
                        admin.getLastName(),
                        admin.getUsername(),
                        admin.getEmail()
                );
            }
        }
        return null;
    }

    public ClientDTO registerClient(ClientCreateDTO clientCreateDTO) {
        User user = userRepository.findByUsername(clientCreateDTO.getUsername());
        if (user == null) {
            Client client = new Client(
                    clientCreateDTO.getFirstName(),
                    clientCreateDTO.getLastName(),
                    clientCreateDTO.getUsername(),
                    clientCreateDTO.getEmail(),
                    clientCreateDTO.getPassword(),
                    clientCreateDTO.getClientType());
            userRepository.save(client);
            return new ClientDTO(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getUsername(),
                    client.getEmail(),
                    client.getType());
        }
        return null;
    }

    public ManagerDTO registerManager(ManagerCreateDTO managerCreateDTO) {
        User user = userRepository.findByUsername(managerCreateDTO.getUsername());
        if (user == null) {
            Manager manager = new Manager(
                    managerCreateDTO.getFirstName(),
                    managerCreateDTO.getLastName(),
                    managerCreateDTO.getUsername(),
                    managerCreateDTO.getEmail(),
                    managerCreateDTO.getPassword());
            userRepository.save(manager);
            return new ManagerDTO(
                    manager.getFirstName(),
                    manager.getLastName(),
                    manager.getUsername(),
                    manager.getEmail());
        }
        return null;
    }

    public AdminDTO registerAdmin(AdminCreateDTO adminCreateDTO) {
        User user = userRepository.findByUsername(adminCreateDTO.getUsername());
        if (user == null) {
            Admin admin = new Admin(
                    adminCreateDTO.getFirstName(),
                    adminCreateDTO.getLastName(),
                    adminCreateDTO.getUsername(),
                    adminCreateDTO.getEmail(),
                    adminCreateDTO.getPassword());
            userRepository.save(admin);
            return new AdminDTO(
                    admin.getFirstName(),
                    admin.getLastName(),
                    admin.getUsername(),
                    admin.getEmail());
        }
        return null;
    }

    public ClientDTO getClient(String username) {
        User user = userRepository.findByUsername(username);
        if (user instanceof Client client) {
            return new ClientDTO(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getUsername(),
                    client.getEmail(),
                    client.getType());
        }
        return null;
    }

    public List<UserDTO> getUsersByPartialUsername(String partialUsername) {
        List<User> users = userRepository.findUsersByPartialUsername(partialUsername);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public ClientDTO setClientType(String username, ClientType clientType) {
        User user = userRepository.findByUsername(username);
        if (user instanceof Client client) {
            client.setType(clientType);
            userRepository.update(client);
            return new ClientDTO(
                    client.getFirstName(),
                    client.getLastName(),
                    client.getUsername(),
                    client.getEmail(),
                    client.getType()
            );
        }
        return null;
    }

    public List<ClientDTO> findAllClientsByClientType(ClientType clientType) {
        List<User> users = userRepository.findAllClientsByClientType(clientType);
        List<Client> clients = new ArrayList<>();
        for(User user : users) {
            if (user instanceof Client client) {
                clients.add(client);
            }
        }
        return clients.stream()
                .map(client -> new ClientDTO(
                        client.getFirstName(),
                        client.getLastName(),
                        client.getUsername(),
                        client.getEmail(),
                        client.getType()
                ))
                .collect(Collectors.toList());
    }

    public void activateAccount(String username) {
        userRepository.activateUser(username);
    }

    public void deactivateAccount(String username) {
        userRepository.deactivateUser(username);
    }

    public List<UserDTO> findAll(UserRole userRole) {
        List<User> users = userRepository.findAll(userRole);
        return userConverter.userListToUserDTOListConverter(users);
    }

    public UserDTO getUserByID(ObjectId userID) {
        User user = userRepository.findByID(userID);
        return userConverter.userToUserDTOConverter(user);
    }
}
