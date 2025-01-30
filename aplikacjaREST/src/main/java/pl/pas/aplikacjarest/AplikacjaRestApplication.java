package pl.pas.aplikacjarest;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AplikacjaRestApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        System.setProperty("SSL_KEYSTORE_PASSWORD", dotenv.get("SSL_KEYSTORE_PASSWORD"));
        System.setProperty("JWT_SECRET", dotenv.get("JWT_SECRET"));
        System.setProperty("JWS_SECRET", dotenv.get("JWS_SECRET"));
        SpringApplication.run(AplikacjaRestApplication.class, args);
    }

}
