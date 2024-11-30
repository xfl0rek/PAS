package pl.pas.aplikacjamvc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pas.aplikacjamvc.dto.UserDTO;

@Service
public class UserService {
    private final WebClient webClient;

    public UserService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080/api/users/").build();
    }

    public void register(UserDTO userDTO) {
        webClient.post()
                .uri("/register")
                .bodyValue(userDTO)
                .retrieve()
                .bodyToMono(UserDTO.class)
                .block();
    }
}
