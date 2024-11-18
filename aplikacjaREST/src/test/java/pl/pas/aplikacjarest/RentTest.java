package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

public class RentTest {

    private UserRepository userRepository = new UserRepository();
    private RoomRepository roomRepository = new RoomRepository();
    private RentRepository rentRepository = new RentRepository();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterEach
    void cleanUp() {
        userRepository.getDatabase().getCollection("users", User.class).drop();
        roomRepository.getDatabase().getCollection("rooms", Room.class).drop();
        rentRepository.getDatabase().getCollection("rents", Rent.class).drop();
    }

    @Test
    void rentRoomPositiveTest() {

    }
}
