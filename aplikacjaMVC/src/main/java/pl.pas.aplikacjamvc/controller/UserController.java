package pl.pas.aplikacjamvc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pas.aplikacjamvc.dto.UserDTO;
import pl.pas.aplikacjamvc.model.User;
import pl.pas.aplikacjamvc.model.UserRole;

@Controller
public class UserController {


    public UserController() {
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
        UserDTO userDTO = new UserDTO(firstName, lastName,
                username, email, password, UserRole.CLIENT);
        model.addAttribute("userDTO", userDTO);
        WebClient webClient = WebClient.builder().build();
        UserDTO respone = webClient.post().uri("http://localhost:8080/api/users/register").bodyValue(userDTO).retrieve()
                .bodyToMono(UserDTO.class)
                .block();
        System.out.println(respone.getUsername());
        //userService.register(userDTO);
        return "home";
    }

    @GetMapping("/test")
    public String Test (Model model) {
        WebClient webClient = WebClient.builder().build();
        UserDTO userDTO = webClient.get().uri("http://localhost:8080/api/users/6749d5d7c7fa8268d9f95ae3").retrieve().bodyToMono(UserDTO.class).block();
        System.out.println(userDTO.getUsername());
        System.out.println(userDTO.getEmail());
        return "home";
    }
}
