package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.exception.UserNotFoundException;
import pl.pas.aplikacjarest.model.UserRole;
import pl.pas.aplikacjarest.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserDTO> getClient(@RequestParam String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getUsersByPartialUsername")
    @CrossOrigin(origins = "http://localhost:9090")
    public ResponseEntity<List<UserDTO>> getUsersByPartialUsername(@RequestParam String partialUsername) {
        List<UserDTO> userDTOs = userService.getUsersByPartialUsername(partialUsername);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/activateAccount/{id}")
    public ResponseEntity<Void> activateAccount(@PathVariable String id) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        userService.activateAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/deactivateAccount/{id}")
    public ResponseEntity<Void> deactivateAccount(@PathVariable String id) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        userService.deactivateAccount(userID);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/changeUserRole/{id}")
    public ResponseEntity<Void> changeUserRole(@PathVariable String id, @RequestParam UserRole userRole) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        userService.changeUserRole(userID, userRole);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/getAllUsersByRole")
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam UserRole userRole) {
        List<UserDTO> userDTOs = userService.findAllByRole(userRole);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.findAll();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable String id) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        UserDTO userDTO = userService.getUserByID(userID);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        userService.updateUser(userID, userDTO);
        return ResponseEntity.noContent().build();
    }

}
