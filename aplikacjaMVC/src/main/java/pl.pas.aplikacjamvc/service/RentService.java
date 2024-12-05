package pl.pas.aplikacjamvc.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import pl.pas.aplikacjamvc.dto.RentDTO;
import pl.pas.aplikacjamvc.exception.AppException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RentService {
    private final WebClient webClient;

    public RentService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080/api/rents/").build();
    }

    public void rentRoom(RentDTO rentDTO) {
        try {
            webClient.post()
                .uri("/")
                .bodyValue(rentDTO)
                .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new AppException(errorBody, response.statusCode().value())))
                    )
                .bodyToMono(RentDTO.class)
                .block();

    } catch (WebClientResponseException e) {
            throw new AppException(e.getResponseBodyAsString(), e.getStatusCode().value());
    }
    }


    public List<RentDTO> getAllRents() {
        try {
            return webClient.get()
                    .uri("/getAllRents")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new AppException(errorBody, response.statusCode().value())))
                    )
                    .bodyToMono(new ParameterizedTypeReference<List<RentDTO>>() {})
                    .block();
        } catch (WebClientResponseException e) {
            throw new AppException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }

    public void returnRoom(String id, LocalDateTime endTime) {
        try {
            webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/returnRoom/{id}")
                            .queryParam("endTime", endTime.toString())
                            .build(id))
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(errorBody -> Mono.error(new AppException(errorBody, response.statusCode().value())))
                    )
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientResponseException e) {
            throw new AppException(e.getResponseBodyAsString(), e.getStatusCode().value());
        }
    }
}
