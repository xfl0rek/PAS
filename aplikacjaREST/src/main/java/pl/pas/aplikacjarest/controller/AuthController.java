package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.ClientCreateDTO;
import pl.pas.aplikacjarest.dto.ClientDTO;
import pl.pas.aplikacjarest.dto.LoginDTO;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.service.UserService;

@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientCreateDTO clientCreateDTO) {
        Client client = userService.registerClient(clientCreateDTO);
        ClientDTO clientDTO = new ClientDTO(client.getFirstName(), client.getLastName(),
                                            client.getEmail(), client.getPassword(), client.getType());
        return ResponseEntity.ok().body(clientDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ClientDTO> loginClient(@RequestBody LoginDTO loginDTO) {
        Client client = userService.loginClient(loginDTO);
        ClientDTO clientDTO = new ClientDTO(client.getFirstName(), client.getLastName(),
                client.getEmail(), client.getPassword(), client.getType());
        return ResponseEntity.ok().body(clientDTO);
    }
}
