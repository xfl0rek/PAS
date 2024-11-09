package pl.pas.aplikacjarest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.pas.aplikacjarest.service.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String firstName,
                               @RequestParam String lastName,
                               @RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               RedirectAttributes redirectAttributes) {
        if (userService.getUserByUsername(username) != null) {
            redirectAttributes.addFlashAttribute("message", "Username already exists!");
            return "redirect:/";
        }

        if (userService.getUserByEmail(email) != null) {
            redirectAttributes.addFlashAttribute("message", "Email already exists!");
            return "redirect:/";
        }

        userService.registerUser(firstName, lastName, username, email, password);
        return "redirect:/home";
    }
}
