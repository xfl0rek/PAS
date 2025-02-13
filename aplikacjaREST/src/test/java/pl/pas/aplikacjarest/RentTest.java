package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.RentDTO;
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
        RestAssured.basePath = "/api/rents";
    }

    @AfterEach
    void cleanUp() {
        userRepository.getDatabase().getCollection("users", User.class).drop();
        roomRepository.getDatabase().getCollection("rooms", Room.class).drop();
        rentRepository.getDatabase().getCollection("rents", Rent.class).drop();
    }

    @Test
    void rentRoomPositiveTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);

        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0), null);
        RestAssured.given()
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/")
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

        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0),
                LocalDateTime.of(2023, 11, 19, 14, 30, 0));

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/returnRoom/{id}")
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
                .put("/{id}")
                .then()
                .statusCode(204);

        Rent updatedRent = rentRepository.findByID(rentID);

        Assertions.assertEquals(
                LocalDateTime.of(2023, 11, 26, 14, 30, 0), updatedRent.getBeginTime());
    }

    @Test
    void deleteRentTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        ObjectId roomID = roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204);

        Assertions.assertNull(rentRepository.findByID(rentID));
        Assertions.assertEquals(0, roomRepository.getRoomByID(roomID).getRented());
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
                .get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("clientUsername", equalTo("jhymel"))
                .body("roomNumber", equalTo(1))
                .body("beginTime", equalTo("2023-11-18T14:30:00"))
                .body("endTime", equalTo(null));
    }

    @Test
    void getAllActiveRentsForUserPositiveTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        Room room1 = new Room(1, 1000, 2);
        Room room2 = new Room(2, 1800, 4);
        Room room3 = new Room(3, 2000, 3);
        Rent rent1 = new Rent(client, room1, LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        Rent rent2 = new Rent(client, room2, LocalDateTime.of(2023, 1, 1, 12, 47, 0));
        Rent rent3 = new Rent(client, room3, LocalDateTime.of(2023, 9, 7, 13, 20, 0));

        ObjectId clientID = userRepository.save(client);
        client.setId(clientID);
        userRepository.update(client);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        ObjectId rent1ID = rentRepository.create(rent1);
        ObjectId rent2ID = rentRepository.create(rent2);
        ObjectId rent3ID = rentRepository.create(rent3);

        rent2.setId(rent2ID);
        rent2.endRent(LocalDateTime.of(2023, 9, 17, 23, 20, 0));
        rentRepository.update(rent2);

        RestAssured.given().
                pathParam("id", clientID.toString())
                .when()
                .get("/getAllActiveRentsForUser/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].clientUsername", equalTo("jhymel"))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].clientUsername", equalTo("jhymel"))
                .body("[1].roomNumber", equalTo(3));
    }

    @Test
    void getAllArchiveRentsForUserPositiveTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        Room room1 = new Room(1, 1000, 2);
        Room room2 = new Room(2, 1800, 4);
        Room room3 = new Room(3, 2000, 3);
        Rent rent1 = new Rent(client, room1, LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        Rent rent2 = new Rent(client, room2, LocalDateTime.of(2023, 1, 1, 12, 47, 0));
        Rent rent3 = new Rent(client, room3, LocalDateTime.of(2023, 9, 7, 13, 20, 0));

        ObjectId clientID = userRepository.save(client);
        client.setId(clientID);
        userRepository.update(client);
        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        ObjectId rent1ID = rentRepository.create(rent1);
        ObjectId rent2ID = rentRepository.create(rent2);
        ObjectId rent3ID = rentRepository.create(rent3);
        rent1.setId(rent1ID);
        rent3.setId(rent3ID);
        rent1.endRent(LocalDateTime.of(2023, 11, 20, 14, 30, 0));
        rent3.endRent(LocalDateTime.of(2023, 9, 17, 23, 20, 0));
        rentRepository.update(rent1);
        rentRepository.update(rent3);

        RestAssured.given().
                pathParam("id", clientID.toString())
                .when()
                .get("/getAllArchiveRentsForUser/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].clientUsername", equalTo("jhymel"))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].clientUsername", equalTo("jhymel"))
                .body("[1].roomNumber", equalTo(3));
    }

    @Test
    void getAllActiveRentsForRoomPositiveTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client1);
        Room room = new Room(1, 1000, 2);
        ObjectId roomId = roomRepository.save(room);
        Rent rent = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        rentRepository.create(rent);

        RestAssured.given()
                .pathParam("id", roomId.toString())
                .when()
                .get("/getAllActiveRentsForRoom/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(1))
                .body("[0].clientUsername", equalTo("jhymel"))
                .body("[0].roomNumber", equalTo(1));
    }

    @Test
    void getAllArchiveRentsForRoomPositiveTest() {
        Client client1 = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        Client client2 = new Client("John", "Bug", "jbuggy", "jbug@example.com", "12213425");
        Client client3 = new Client("Sebastian", "Alvarez", "sentino", "tatuazyk123@gmail.com", "4756238511");
        userRepository.save(client1);
        userRepository.save(client2);
        userRepository.save(client3);

        Room room = new Room(1, 1000, 2);
        ObjectId roomId = roomRepository.save(room);
        Rent rent1 = new Rent(client1, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rent1Id = rentRepository.create(rent1);
        rent1.setId(rent1Id);
        rent1.endRent(LocalDateTime.of(2023, 11, 19, 14, 30, 0));
        rentRepository.update(rent1);

        Rent rent2 = new Rent(client2, room,
                LocalDateTime.of(2023, 11, 20, 14, 30, 0));
        ObjectId rent2Id = rentRepository.create(rent2);
        rent2.setId(rent2Id);
        rent2.endRent(LocalDateTime.of(2023, 11, 25, 14, 30, 0));
        rentRepository.update(rent2);

        Rent rent3 = new Rent(client3, room,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0));
        ObjectId rent3Id = rentRepository.create(rent3);
        rent3.setId(rent3Id);
        rent3.endRent(LocalDateTime.of(2023, 11, 30, 14, 30, 0));
        rentRepository.update(rent3);

        RestAssured.given()
                .pathParam("id", roomId.toString())
                .when()
                .get("/getAllArchiveRentsForRoom/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(3))
                .body("[0].clientUsername", equalTo("jhymel"))
                .body("[0].roomNumber", equalTo(1))
                .body("[1].clientUsername", equalTo("jbuggy"))
                .body("[1].roomNumber", equalTo(1))
                .body("[2].clientUsername", equalTo("sentino"))
                .body("[2].roomNumber", equalTo(1));
    }

    @Test
    void invalidArgumentsPassedTest() {
        RentDTO invalidRentDTO = new RentDTO("ab", 0, null, null);
        RestAssured.given()
                .body(invalidRentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/")
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
                .post("/")
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

        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0),
                LocalDateTime.of(2023, 11, 19, 14, 30, 0));

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/returnRoom/{id}")
                .then()
                .statusCode(200);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/returnRoom/{id}")
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
                .put("/{id}")
                .then()
                .statusCode(404);

        Assertions.assertEquals(
                LocalDateTime.of(2023, 11, 18, 14, 30, 0),
                rentRepository.findByID(rentID).getBeginTime()
        );
    }

    @Test
    void deleteRentNegativeTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);
        Room room = new Room(1, 1000, 2);
        roomRepository.save(room);
        Rent rent = new Rent(client, room,
                LocalDateTime.of(2023, 11, 18, 14, 30, 0));
        ObjectId rentID = rentRepository.create(rent);

        RentDTO rentDTO = new RentDTO("jhymel", 1,
                LocalDateTime.of(2023, 11, 26, 14, 30, 0),
                LocalDateTime.of(2023, 11, 19, 14, 30, 0));

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .body(rentDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/returnRoom/{id}")
                .then()
                .statusCode(200);

        RestAssured.given()
                .pathParam("id", rentID.toString())
                .when()
                .delete("/{id}")
                .then()
                .statusCode(400);

        Assertions.assertNotNull(rentRepository.findByID(rentID));
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
                .get("/{id}")
                .then()
                .statusCode(404);
    }
}
