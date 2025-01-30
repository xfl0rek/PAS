package pl.pas.aplikacjarest.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.ChangePasswordDTO;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.exception.UserNotFoundException;
import pl.pas.aplikacjarest.model.UserRole;
import pl.pas.aplikacjarest.security.Jws;
import pl.pas.aplikacjarest.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final Jws jws;

    @Autowired
    public UserController(UserService userService, Jws jws) {
        this.userService = userService;
        this.jws = jws;
    }

    @GetMapping("/getUser")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
    public ResponseEntity<UserDTO> getClient(@RequestParam String username) {
        UserDTO userDTO = userService.getUser(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/getUsersByPartialUsername")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getUsersByPartialUsername(@RequestParam String partialUsername) {
        List<UserDTO> userDTOs = userService.getUsersByPartialUsername(partialUsername);
        return ResponseEntity.ok(userDTOs);
    }

    @PostMapping("/activateAccount/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
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
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
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
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getAllUsersByRole(@RequestParam UserRole userRole) {
        List<UserDTO> userDTOs = userService.findAllByRole(userRole);
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userDTOs = userService.findAll();
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable String id) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        String generatedJws = jws.generateJws(id);
        UserDTO userDTO = userService.getUserByID(userID);
        return ResponseEntity.ok()
                .header("JWS", generatedJws)
                .body(userDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO, @RequestHeader("JWS") String jwsHeader) {
        ObjectId userID;
        try {
            userID = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        boolean isValid = jws.validateJws(jwsHeader, id);
        userService.updateUser(userID, userDTO);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/changePassword/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> changePassword(@PathVariable String id, @RequestBody ChangePasswordDTO changePasswordDTO) {
        ObjectId userId;
        try {
            userId = new ObjectId(id);
        } catch (Exception e) {
            throw new UserNotFoundException("User not found");
        }
        userService.changePassword(userId, changePasswordDTO);
        return ResponseEntity.noContent().build();
    }
}
