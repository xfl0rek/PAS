package pl.pas.aplikacjamvc.service;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.pas.aplikacjamvc.dto.UserDTO;
import pl.pas.aplikacjamvc.exception.AppException;
import reactor.core.publisher.Mono;

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

//    public static ExchangeFilterFunction errorHandler() {
//        return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//            if (clientResponse.statusCode().is5xxServerError()) {
//                return clientResponse.bodyToMono(String.class)
//                        .flatMap(errorBody -> Mono.error(new UserDefinedException1(errorBody)));
//            } else if (clientResponse.statusCode().is4xxClientError()) {
//                return clientResponse.bodyToMono(String.class)
//                        .flatMap(errorBody -> Mono.error(new UserDefinedException2(errorBody)));
//            } else {
//                return Mono.just(clientResponse);
//            }
//        });
    //}
}
