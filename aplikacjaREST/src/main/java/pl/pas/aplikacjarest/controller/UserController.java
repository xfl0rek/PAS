package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.model.ClientType;
import pl.pas.aplikacjarest.service.UserService;

import java.util.List;

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

    @PostMapping("/admin/setClientType")
    public ResponseEntity<ClientDTO> setClientType(@RequestParam String username,
                                                   @RequestParam ClientType clientType) {
        ClientDTO clientDTO = userService.setClientType(username, clientType);
        return ResponseEntity.ok(clientDTO);
    }

    @GetMapping("/admin/getAllClientsByClientType")
    public ResponseEntity<List<ClientDTO>> getAllClientsByClientType(@RequestParam ClientType clientType) {
        List<ClientDTO> clientDTOs = userService.findAllClientsByClientType(clientType);
        return ResponseEntity.ok(clientDTOs);
    }

    @PostMapping("/admin/activateAccount")
    public void activateAccount(@RequestParam String username) {
        userService.activateAccount(username);
    }


    @PostMapping("/admin/deactivateAccount")
    public void deactivateAccount(@RequestParam String username) {
        userService.deactivateAccount(username);
    }



//    @GetMapping("/admin/getAllUsersByRole")
//    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam UserRole userRole) {
//        List<UserDTO> userDTOs = userService.findAllUsersByUserRole(userRole);
//        return ResponseEntity.ok(userDTOs);
//    }
}
