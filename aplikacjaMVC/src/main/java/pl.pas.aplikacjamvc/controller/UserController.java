package pl.pas.aplikacjamvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pas.aplikacjamvc.dto.UserDTO;
import pl.pas.aplikacjamvc.exception.AppException;
import pl.pas.aplikacjamvc.model.UserRole;
import pl.pas.aplikacjamvc.service.UserService;
import reactor.core.publisher.Mono;

import org.springframework.http.HttpStatusCode;

import java.util.List;

@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showForm (Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String firstName,
                           @RequestParam String lastName,
                           @RequestParam String username,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        UserDTO userDTO = userService.register(new UserDTO(firstName, lastName,
                username, email, password, UserRole.CLIENT));
        model.addAttribute("userDTO", userDTO);
        return "redirect:/home";
    }

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<UserDTO> userDTOS = userService.getAllUsers();
        model.addAttribute("userDTO", userDTOS);
        return "users";
    }
}
