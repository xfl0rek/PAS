package pl.pas.aplikacjarest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.pas.aplikacjarest.dto.*;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.model.UserRole;
import pl.pas.aplikacjarest.security.JwtUtils;
import pl.pas.aplikacjarest.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class AuthController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerClient(@Valid @RequestBody UserDTO userDTO) {
        UserDTO user = userService.registerUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword())
        );

        UserDetails userDetails = userService.loadUserByUsername(loginDTO.getUsername());

        String jwtToken = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new TokenResponseDTO(jwtToken));
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        userService.logout(authHeader);
    }


    @GetMapping("/checkAuth")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AuthResponseDto> checkAuth(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwtToken = authHeader.substring(7);

        String username = jwtUtils.extractUsername(jwtToken);
        UserDetails userDetails = userService.loadUserByUsername(username);


        UserDTO user = userService.getUser(username);
        UserRole role = user.getUserRole();

        AuthResponseDto authResponseDto = new AuthResponseDto(username, role);
        return ResponseEntity.ok(authResponseDto);
    }
}
