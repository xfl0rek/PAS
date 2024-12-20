package pl.pas.aplikacjamvc.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.pas.aplikacjamvc.dto.UserDTO;
import pl.pas.aplikacjamvc.exception.AppException;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {
    private final WebClient webClient;

    public UserService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080/api/users/").build();
    }

    public UserDTO register(UserDTO userDTO) {
        try {
            return webClient.post()
                    .uri("/register")
                    .bodyValue(userDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new AppException(errorBody, response.statusCode().value())))
                    )
                    .bodyToMono(UserDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new AppException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    public List<UserDTO> getAllUsers() {
        try {
            return webClient.get()
                    .uri("/")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new AppException(errorBody, response.statusCode().value())))
                    )
                    .bodyToMono(new ParameterizedTypeReference<List<UserDTO>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            throw new AppException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
