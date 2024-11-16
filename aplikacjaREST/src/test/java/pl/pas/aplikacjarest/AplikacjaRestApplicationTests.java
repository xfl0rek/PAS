package pl.pas.aplikacjarest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.time.LocalDateTime;

@SpringBootTest
class AplikacjaRestApplicationTests {

    @Test
    void contextLoads() {
//        UserRepository userRepository = new UserRepository();
//        User client = userRepository.findByUsername("jhymel");
//        System.out.println(client.getClass());
//        System.out.println(client.getPassword());
//        System.out.println(client.getUsername());
        System.out.println(LocalDateTime.now());
    }

}
