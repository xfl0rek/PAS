package pl.pas.aplikacjarest;

import com.mongodb.client.MongoCollection;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.RoomDTO;
import pl.pas.aplikacjarest.model.Room;
import pl.pas.aplikacjarest.repository.RoomRepository;

import static org.hamcrest.Matchers.equalTo;

public class RoomTest {

    private RoomRepository roomRepository = new RoomRepository();

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterEach
    void cleanUp() {
        roomRepository.getDatabase().getCollection("rooms", Room.class).drop();
    }

    @Test
    void roomCreatePositiveTest() {
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201)
                .body("roomNumber", equalTo(1))
                .body("basePrice", equalTo(1000))
                .body("roomCapacity", equalTo(2));
    }

    @Test
    void roomUpdatePositiveTest() {
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);

        Room room = roomRepository.findByRoomNumber(1);

        RoomDTO roomDTOUpdated = new RoomDTO(1, 2000, 3);
        RestAssured.given()
                .pathParams("id", room.getId().toString())
                .body(roomDTOUpdated)
                .contentType("application/json")
                .when()
                .post("/manager/{id}")
                .then()
                .statusCode(204);

        room = roomRepository.findByRoomNumber(1);
        Assertions.assertEquals(1, room.getRoomNumber());
        Assertions.assertEquals(2000, room.getBasePrice());
        Assertions.assertEquals(3, room.getRoomCapacity());
    }

    @Test
    void roomDeletePositiveTest() {
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);

        Room room = roomRepository.findByRoomNumber(1);
        Assertions.assertNotNull(room);

        RestAssured.given()
                .pathParams("id", room.getId().toString())
                .when()
                .post("/admin/deleteRoom/{id}")
                .then()
                .statusCode(204);

        room = roomRepository.findByRoomNumber(1);
        Assertions.assertNull(room);
    }

    @Test
    void getRoomsByRoomCapacityTest() {
        RoomDTO roomDTO = new RoomDTO(1, 1000, 2);
        RestAssured.given()
                .body(roomDTO)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RoomDTO roomDTO2 = new RoomDTO(2, 1200, 3);
        RestAssured.given()
                .body(roomDTO2)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);
        RoomDTO roomDTO3 = new RoomDTO(3, 1600, 2);
        RestAssured.given()
                .body(roomDTO3)
                .contentType("application/json")
                .when()
                .post("/manager/createRoom")
                .then()
                .statusCode(201);

        RestAssured.given()
                .queryParam("roomCapacity", 2)
                .when()
                .get("/manager/getRoomsByRoomCapacity")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].roomNumber", equalTo(3));

    }

    ///manager/getRoomsByRoomCapacity
}
