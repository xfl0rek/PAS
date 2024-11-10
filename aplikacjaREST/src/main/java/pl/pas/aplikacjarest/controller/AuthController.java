package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.service.UserService;

@RestController
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/client/register")
    public ResponseEntity<ClientDTO> registerClient(@RequestBody ClientCreateDTO clientCreateDTO) {
        ClientDTO clientDTO = userService.registerClient(clientCreateDTO);
        return ResponseEntity.ok().body(clientDTO);
    }

    @PostMapping("/manager/register")
    public ResponseEntity<ManagerDTO> registerManager(@RequestBody ManagerCreateDTO managerCreateDTO) {
        ManagerDTO managerDTO = userService.registerManager(managerCreateDTO);
        return ResponseEntity.ok().body(managerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<ClientDTO> loginClient(@RequestBody LoginDTO loginDTO) {
        Client client = userService.loginClient(loginDTO);
        ClientDTO clientDTO = new ClientDTO(client.getFirstName(), client.getLastName(),
                client.getUsername(), client.getEmail(), client.getType());
        return ResponseEntity.ok().body(clientDTO);
    }
}
