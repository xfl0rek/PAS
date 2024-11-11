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
import pl.pas.aplikacjarest.model.Admin;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Manager;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.UserRepository;

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
                        client.getId(),
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
                        manager.getId(),
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
                        admin.getId(),
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
                clientCreateDTO.getId(),
                clientCreateDTO.getFirstName(),
                clientCreateDTO.getLastName(),
                clientCreateDTO.getUsername(),
                clientCreateDTO.getEmail(),
                clientCreateDTO.getPassword(),
                clientCreateDTO.getClientType());
        userRepository.save(client);
        return new ClientDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getUsername(),
                client.getEmail(),
                client.getType());
    }

    public ManagerDTO registerManager(ManagerCreateDTO managerCreateDTO) {
        Manager manager = new Manager(
                managerCreateDTO.getId(),
                managerCreateDTO.getFirstName(),
                managerCreateDTO.getLastName(),
                managerCreateDTO.getUsername(),
                managerCreateDTO.getEmail(),
                managerCreateDTO.getPassword());
        userRepository.save(manager);
        return new ManagerDTO(
                manager.getId(),
                manager.getFirstName(),
                manager.getLastName(),
                manager.getUsername(),
                manager.getEmail());
    }

    public AdminDTO registerAdmin(AdminCreateDTO adminCreateDTO) {
        Admin admin = new Admin(
                adminCreateDTO.getId(),
                adminCreateDTO.getFirstName(),
                adminCreateDTO.getLastName(),
                adminCreateDTO.getUsername(),
                adminCreateDTO.getEmail(),
                adminCreateDTO.getPassword());
        userRepository.save(admin);
        return new AdminDTO(
                admin.getId(),
                admin.getFirstName(),
                admin.getLastName(),
                admin.getUsername(),
                admin.getEmail());
    }

    //TODO: ogarnac rzutowanie
    public ClientDTO getClient(String username) {
        Client client = (Client) userRepository.findByUsername(username);
        return new ClientDTO(
                client.getId(),
                client.getFirstName(),
                client.getLastName(),
                client.getUsername(),
                client.getEmail(),
                client.getType());
    }
}
