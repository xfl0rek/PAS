package pl.pas.aplikacjamvc.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import pl.pas.aplikacjamvc.dto.RentDTO;

@Service
public class RentService {
    private final WebClient webClient;

    public RentService() {
        this.webClient = WebClient.builder().baseUrl("http://localhost:8080/api/rents/").build();
    }

    public void rentRoom(RentDTO rentDTO) {
        webClient.post()
                .uri("/")
                .bodyValue(rentDTO)
                .retrieve()
                .bodyToMono(RentDTO.class)
                .block();
    }
}
