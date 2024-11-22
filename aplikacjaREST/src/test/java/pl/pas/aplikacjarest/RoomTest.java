package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Rent;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.repository.RentRepository;
import pl.pas.aplikacjarest.repository.RoomRepository;
import pl.pas.aplikacjarest.repository.UserRepository;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;

public class RoomTest {

    private RoomRepository roomRepository = new RoomRepository();
    private UserRepository userRepository = new UserRepository();
    private RentRepository rentRepository = new RentRepository();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "/api/rooms";
    }

    @AfterEach
    void cleanUp() {
        userRepository.getDatabase().getCollection("users", User.class).drop();
        roomRepository.getDatabase().getCollection("rooms", Room.class).drop();
        rentRepository.getDatabase().getCollection("rents", Rent.class).drop();
    }

    @Test
    void roomCreatePositiveTest() {
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/createRoom")
                .then()
                .statusCode(201)
                .body("roomNumber", equalTo(1))
                .body("basePrice", equalTo(1000))
                .body("roomCapacity", equalTo(2));
    }

    @Test
    void roomUpdatePositiveTest() {
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);

        RoomDTO roomDTOUpdated = new RoomDTO(1, 2000, 3);
        RestAssured.given()
                .pathParams("id", roomID.toString())
                .body(roomDTOUpdated)
                .contentType("application/json")
                .when()
                .put("/{id}")
                .then()
                .statusCode(204);

        room = roomRepository.getRoomByID(roomID);
        Assertions.assertEquals(1, room.getRoomNumber());
        Assertions.assertEquals(2000, room.getBasePrice());
        Assertions.assertEquals(3, room.getRoomCapacity());
    }

    @Test
    void roomDeletePositiveTest() {
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);
        Assertions.assertNotNull(room);

        RestAssured.given()
                .pathParams("id", roomID.toString())
                .when()
                .delete("/deleteRoom/{id}")
                .then()
                .statusCode(204);

        room = roomRepository.getRoomByID(roomID);
        Assertions.assertNull(room);
    }

    @Test
    void getRoomsByRoomCapacityTest() {
        Room room1 = new Room(1, 1000, 2);
        Room room2 = new Room(2, 1300, 3);
        Room room3 = new Room(3, 1600, 2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);

        RestAssured.given()
                .queryParam("roomCapacity", 2)
                .when()
                .get("/getRoomsByRoomCapacity")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].roomNumber", equalTo(3));
    }

    @Test
    void getRoomByIDTest() {
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);

        RestAssured.given()
                .pathParams("id", roomID.toString())
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("roomNumber", equalTo(1))
                .body("basePrice", equalTo(1000))
                .body("roomCapacity", equalTo(2));
    }

    @Test
    void getRoomByBasePriceTest() {
        Room room1 = new Room(1, 1000, 2);
        Room room2 = new Room(2, 1000, 3);
        Room room3 = new Room(3, 1600, 2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);

        RestAssured.given()
                .queryParam("basePrice", 1000)
                .when()
                .get("/getRoomsByBasePrice")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].roomNumber", equalTo(2));
    }

    @Test
    void getAllRoomsPositiveTest() {
        Room room1 = new Room(1, 1000, 2);
        Room room2 = new Room(2, 1000, 3);
        Room room3 = new Room(3, 1600, 2);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);

        RestAssured.given()
                .when()
                .get("/getAllRooms")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(3))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].roomNumber", equalTo(2))
                .body("[2].roomNumber", equalTo(3));
    }

    //Negative tests

    @Test
    void invalidArgumentsPassedTest() {
        RoomDTO roomDTO = new RoomDTO(0, 10, -1);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/createRoom")
                .then()
                .statusCode(400);
    }

    @Test
    void roomCreateNegativeTest() {
        RoomDTO roomDTO1 = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO1)
                .contentType("application/json")
                .when()
                .post("/createRoom")
                .then()
                .statusCode(201);
        RoomDTO roomDTO2 = new RoomDTO(1, 1000, 3);
        RestAssured.given()
                .body(roomDTO2)
                .contentType("application/json")
                .when()
                .post("/createRoom")
                .then()
                .statusCode(400);
    }

    @Test
    void roomUpdateNegativeTest() {
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);

        RoomDTO roomDTOUpdated = new RoomDTO(1, 2000, 3);
        RestAssured.given()
                .pathParams("id", "zleid")
                .body(roomDTOUpdated)
                .contentType("application/json")
                .when()
                .put("/{id}")
                .then()
                .statusCode(500);

        Room room2 = roomRepository.getRoomByID(roomID);
        Assertions.assertEquals(1000, room2.getBasePrice());
        Assertions.assertEquals(2, room2.getRoomCapacity());

    }

    @Test
    void deleteRentedRoomNegativeTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);
        Rent rent = new Rent(client, room, LocalDateTime.now());
        rentRepository.create(rent);
        Assertions.assertNotNull(room);

        RestAssured.given()
                .pathParams("id", roomID.toString())
                .when()
                .delete("/deleteRoom/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    void getRoomByIDNegativeTest() {
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);

        RestAssured.given()
                .pathParams("id", "zleid")
                .when()
                .get("/{id}")
                .then()
                .statusCode(500);
    }
}
