//package pl.pas.aplikacjamvc.service;
//
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.ClientResponse;
//import org.springframework.web.reactive.function.client.WebClient;
//import pl.pas.aplikacjamvc.dto.UserDTO;
//
//@Service
//public class UserService {
//    private final WebClient webClient;
//
//    public UserService(WebClient webClient) {
//        this.webClient = webClient;
//    }
//
//    public void register(UserDTO userDTO) {
//        String api = "http://localhost:8080/api/users/register";
//        ClientResponse clientResponse = webClient.post()
//                .uri(api)
//                .bodyValue(userDTO);
//    }
//}
