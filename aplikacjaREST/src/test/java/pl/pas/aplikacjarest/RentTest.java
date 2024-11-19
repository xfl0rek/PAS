package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.model.UserRole;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;
import java.util.List;

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
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);

        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("clientUsername", equalTo("jhymel"))
                .body("roomNumber", equalTo(1))
                .body("beginTime", equalTo("2023-11-18T14:30:00"))
                .body("endTime", equalTo(null));
    }

    @Test
    void returnRoomPositiveTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(201);

        List<Rent> rents = rentRepository.findAll();
        System.out.println(rents.get(0).getId().toString());

        RestAssured.given()
                .pathParam("id", rents.get(0).getId().toString())
                .queryParam("endTime", "2023-11-19T14:30:00")
                .contentType(ContentType.JSON)
                .when()
                .post("/client/returnRoom/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("clientUsername", equalTo("jhymel"))
                .body("roomNumber", equalTo(1))
                .body("beginTime", equalTo("2023-11-18T14:30:00"))
                .body("endTime", equalTo("2023-11-19T14:30:00"));
    }

    @Test
    void updateRentPositiveTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(201);

        List<Rent> rents = rentRepository.findAll();
        RentDTO updateRent = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), null);

        RestAssured.given()
                .pathParam("id", rents.get(0).getId().toString())
                .body(updateRent)
                .contentType(ContentType.JSON)
                .when()
                .post("/manager/updateRent/{id}")
                .then()
                .statusCode(204);

        rents = rentRepository.findAll();
        Assertions.assertEquals(
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), rents.get(0).getBeginTime());
    }

    @Test
    void deleteRentTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(201);

        List<Rent> rents = rentRepository.findAll();
        System.out.println(rents.get(0).getId().toString());

        RestAssured.given()
                .pathParam("id", rents.get(0).getId().toString())
                .queryParam("endTime", "2023-11-19T14:30:00")
                .contentType(ContentType.JSON)
                .when()
                .post("/client/returnRoom/{id}")
                .then()
                .statusCode(200);

        RestAssured.given()
                .pathParam("id", rents.get(0).getId().toString())
                .when()
                .post("/manager/deleteRent/{id}")
                .then()
                .statusCode(204);

        rents = rentRepository.findAll();
        Assertions.assertTrue(rents.isEmpty());
    }

    @Test
    void getRentByIDTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(201);

        List<Rent> rents = rentRepository.findAll();

        RestAssured.given()
                .pathParam("id", rents.get(0).getId().toString())
                .when()
                .get("/manager/getRentByID/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("clientUsername", equalTo("jhymel"))
                .body("roomNumber", equalTo(1))
                .body("beginTime", equalTo("2023-11-18T14:30:00"))
                .body("endTime", equalTo(null));

    }
}
