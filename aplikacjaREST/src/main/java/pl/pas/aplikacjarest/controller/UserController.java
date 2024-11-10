package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/client/getClient")
    public ResponseEntity<ClientDTO> getClient(@RequestParam String username) {
        ClientDTO clientDTO = userService.getClient(username);
        return ResponseEntity.ok(clientDTO);
    }
}
