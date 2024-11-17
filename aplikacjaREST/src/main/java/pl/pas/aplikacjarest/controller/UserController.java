package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.UserRole;
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
    public ResponseEntity<UserDTO> getClient(@RequestParam String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/manager/getUsersByPartialUsername")
    public ResponseEntity<List<UserDTO>> getUsersByPartialUsername(@RequestParam String partialUsername) {
        List<UserDTO> userDTOs = userService.getUsersByPartialUsername(partialUsername);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/admin/activateAccount")
    public void activateAccount(@RequestParam String username) {
        userService.activateAccount(username);
    }

    @PostMapping("/admin/deactivateAccount")
    public void deactivateAccount(@RequestParam String username) {
        userService.deactivateAccount(username);
    }

    @PostMapping("/admin/changeUserRole")
    public void changeUserRole(@RequestParam String username, UserRole userRole) {
        userService.changeUserRole(username, userRole);
    }

    @GetMapping("/admin/getAllUsersByRole")
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam UserRole userRole) {
        List<UserDTO> userDTOs = userService.findAll(userRole);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/admin/getUserByID")
    public ResponseEntity<UserDTO> getUserByID(@RequestParam ObjectId userID) {
        UserDTO userDTO = userService.getUserByID(userID);
        return ResponseEntity.ok(userDTO);
    }
}
