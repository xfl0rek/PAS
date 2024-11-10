package pl.pas.aplikacjarest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pas.aplikacjarest.dto.ClientCreateDTO;
import pl.pas.aplikacjarest.dto.ClientDTO;
import pl.pas.aplikacjarest.dto.LoginDTO;
import pl.pas.aplikacjarest.model.Client;
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

//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    public User getUserByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }

    public Client registerClient(ClientCreateDTO clientCreateDTO) {
        Client client = new Client(clientCreateDTO.getFirstName(), clientCreateDTO.getLastName(),
                                clientCreateDTO.getUsername(), clientCreateDTO.getEmail(),
                                clientCreateDTO.getPassword(), clientCreateDTO.getClientType());
        userRepository.save(client);
        return client;
    }
}
