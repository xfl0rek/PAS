package pl.pas.aplikacjarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public ManagerDTO registerManager(ManagerCreateDTO managerCreateDTO) {
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

    public AdminDTO registerAdmin(AdminCreateDTO adminCreateDTO) {
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

//    public List<ClientDTO> findAllClients() {
//        List<User> clients = userRepository.findAll(UserRole.CLIENT);
//        return clients.stream()
//                .map(client -> new ClientDTO(
//                        client.getFirstName(),
//                        client.getLastName(),
//                        client.getUsername(),
//                        client.getEmail(),
//                        client.getType()
//                ))
//                .collect(Collectors.toList());
//    }
//
//    public List<ManagerDTO> findAllManagers() {
//        List<User> managers = userRepository.findAll(UserRole.MANAGER);
//    }
}
