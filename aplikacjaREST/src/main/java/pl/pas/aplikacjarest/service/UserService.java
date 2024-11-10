package pl.pas.aplikacjarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.*;
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

    //to tak chyba byc nie moze z tym rzutowaniem
    //TODO: poprawic pozniej
    public Client loginClient(LoginDTO loginDTO) {
        User user = userRepository.findByUsername(loginDTO.getUsername());
        if (user != null && user.getPassword().equals(loginDTO.getPassword())) {
            return (Client) user;
        }
        return null;
    }

    public ClientDTO registerClient(ClientCreateDTO clientCreateDTO) {
        Client client = new Client(clientCreateDTO.getFirstName(), clientCreateDTO.getLastName(),
                                clientCreateDTO.getUsername(), clientCreateDTO.getEmail(),
                                clientCreateDTO.getPassword(), clientCreateDTO.getClientType());
        userRepository.save(client);
        return new ClientDTO(client.getFirstName(), client.getLastName(),
                                client.getUsername(), client.getEmail(), client.getType());
    }

    public ManagerDTO registerManager(ManagerCreateDTO managerCreateDTO) {
        Manager manager = new Manager(managerCreateDTO.getFirstName(), managerCreateDTO.getLastName(),
                                        managerCreateDTO.getUsername(), managerCreateDTO.getEmail(),
                                        managerCreateDTO.getPassword());
        userRepository.save(manager);
        return new ManagerDTO(manager.getFirstName(), manager.getLastName(),
                                manager.getUsername(), manager.getEmail());
    }
}
