package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.RentDTO;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.*;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

import static org.hamcrest.Matchers.equalTo;

import java.time.LocalDateTime;

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
        Client client = new Client("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);
        Rent rent = new Rent(client, room, LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
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

        Room returnedRoom = roomRepository.getRoomByID(roomID);
        Rent returnedRent = rentRepository.findByID(rentID);

        Assertions.assertEquals(0, returnedRoom.getRented());
        Assertions.assertEquals(1000, returnedRent.getRentCost());
        Assertions.assertTrue(returnedRent.isArchive());
    }

    @Test
    void updateRentPositiveTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RentDTO updateRent = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), null);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .body(updateRent)
                .contentType(ContentType.JSON)
                .when()
                .post("/manager/updateRent/{id}")
                .then()
                .statusCode(204);

        Rent updatedRent = rentRepository.findByID(rentID);

        Assertions.assertEquals(
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), updatedRent.getBeginTime());
    }

    @Test
    void deleteRentTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .queryParam("endTime", "2023-11-19T14:30:00")
                .contentType(ContentType.JSON)
                .when()
                .post("/client/returnRoom/{id}")
                .then()
                .statusCode(200);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .when()
                .post("/manager/deleteRent/{id}")
                .then()
                .statusCode(204);

        Assertions.assertNull(rentRepository.findByID(rentID));
    }

    @Test
    void getRentByIDTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
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

    @Test
    void invalidArgumentsPassedTest() {
        RentDTO invalidRentDTO = new RentDTO("ab", 0, null, null);
        RestAssured.given()
                .body(invalidRentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(400);

    }

    @Test
    void rentRoomNegativeTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        rentRepository.create(rent);
        Client client2 = new Client("John", "Bug", "jbug", "jbuggy@example.com", "12345634534");
        RentDTO rentDTO = new RentDTO(client2.getUsername(), room.getRoomNumber(), LocalDateTime.now(), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/client/rentRoom")
                .then()
                .statusCode(400);
    }

    @Test
    void returnAlreadyReturnedRoomNegativeTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .queryParam("endTime", "2023-11-19T14:30:00")
                .contentType(ContentType.JSON)
                .when()
                .post("/client/returnRoom/{id}")
                .then()
                .statusCode(200);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .queryParam("endTime", "2023-11-19T14:30:00")
                .contentType(ContentType.JSON)
                .when()
                .post("/client/returnRoom/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    void updateRentNegativeTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RentDTO updateRent = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), null);

        RestAssured.given()
                .pathParam("id", "zleid")
                .body(updateRent)
                .contentType(ContentType.JSON)
                .when()
                .post("/manager/updateRent/{id}")
                .then()
                .statusCode(500);

        Assertions.assertEquals(
                LocalDateTime.of(2023, 11, 18, 14, 30, 0),
                rentRepository.findByID(rentID).getBeginTime()
        );
    }

    @Test
    void deleteRentNegativeTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .when()
                .post("/manager/deleteRent/{id}")
                .then()
                .statusCode(400);

        Assertions.assertFalse(rentRepository.findByID(rentID).isArchive());
    }

    @Test
    void getRentByIdNegativeTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", "zleid")
                .when()
                .get("/manager/getRentByID/{id}")
                .then()
                .statusCode(500);
    }
}
