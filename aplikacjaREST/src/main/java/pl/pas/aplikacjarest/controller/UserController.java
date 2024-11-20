package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/admin/activateAccount/{id}")
    public ResponseEntity<Void> activateAccount(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        userService.activateAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/deactivateAccount/{id}")
    public ResponseEntity<Void> deactivateAccount(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        userService.deactivateAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/admin/changeUserRole/{id}")
    public ResponseEntity<Void> changeUserRole(@PathVariable String id, @RequestParam UserRole userRole) {
        ObjectId userID = new ObjectId(id);
        userService.changeUserRole(userID, userRole);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/admin/getAllUsersByRole")
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam UserRole userRole) {
        List<UserDTO> userDTOs = userService.findAllByRole(userRole);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/admin/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.findAll();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable String id) {
        ObjectId userID = new ObjectId(id);
        UserDTO userDTO = userService.getUserByID(userID);
        return ResponseEntity.ok(userDTO);
    }
}
