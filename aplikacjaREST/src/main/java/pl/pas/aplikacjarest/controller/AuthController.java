package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.dto.admin.AdminCreateDTO;
import pl.pas.aplikacjarest.dto.admin.AdminDTO;
import pl.pas.aplikacjarest.dto.client.ClientCreateDTO;
import pl.pas.aplikacjarest.dto.client.ClientDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerCreateDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerDTO;
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

    @PostMapping("/admin/register")
    public ResponseEntity<AdminDTO> registerAdmin(@RequestBody AdminCreateDTO adminCreateDTO) {
        AdminDTO adminDTO = userService.registerAdmin(adminCreateDTO);
        return ResponseEntity.ok().body(adminDTO);
    }

    @PostMapping("/client/login")
    public ResponseEntity<ClientDTO> loginClient(@RequestBody LoginDTO loginDTO) {
        ClientDTO clientDTO = userService.loginClient(loginDTO);
        return ResponseEntity.ok().body(clientDTO);
    }
}
