package pl.pas.aplikacjarest;

import com.mongodb.client.MongoCollection;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.LoginDTO;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.User;
import pl.pas.aplikacjarest.model.UserRole;
import pl.pas.aplikacjarest.repository.UserRepository;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {
    private UserRepository userRepository = new UserRepository();
    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @AfterEach
    void cleanUp() {
        userRepository.getDatabase().getCollection("users", User.class).drop();
    }

    @Test
    void registerAndLoginUserPositiveTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Jadwiga"))
                .body("lastName", equalTo("Hymel"))
                .body("username", equalTo("jhymel"))
                .body("email", equalTo("jadwigahymel@example.com"))
                .body("password", equalTo("synaniemawdomu"))
                .body("userRole", equalTo("CLIENT"));

        LoginDTO loginDTO = new LoginDTO("jhymel", "synaniemawdomu");

        RestAssured.given()
                .body(loginDTO)
                .contentType("application/json")
                .when()
                .post("/login")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Jadwiga"))
                .body("lastName", equalTo("Hymel"))
                .body("username", equalTo("jhymel"))
                .body("email", equalTo("jadwigahymel@example.com"))
                .body("password", equalTo("synaniemawdomu"))
                .body("userRole", equalTo("CLIENT"));
    }

    @Test
    void getClientTest() {
        UserDTO userDTO = new UserDTO("John", "Bug", "jbug",
                "jbug@example.com", "123456789" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        RestAssured.given()
                .queryParam("username", "jbug")
                .when()
                .get("/client/getClient")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Bug"))
                .body("username", equalTo("jbug"))
                .body("email", equalTo("jbug@example.com"))
                .body("password", equalTo("123456789"))
                .body("userRole", equalTo("CLIENT"));
    }

    @Test
    void getUsersByPartialUsernameTest() {
        UserDTO userDTO = new UserDTO("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        RestAssured.given()
                .queryParam("partialUsername", "ta")
                .when()
                .get("/manager/getUsersByPartialUsername")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].firstName", equalTo("Sebastian"))
                .body("[0].lastName", equalTo("Alvarez"))
                .body("[0].username", equalTo("tatuazyk123"))
                .body("[0].email", equalTo("sentino@example.com"))
                .body("[0].userRole", equalTo("CLIENT"));
    }

    @Test
    void activateAndDeactivateAccountTest() {
        UserDTO userDTO = new UserDTO("John", "Doe", "johndoe123",
                "john.doe@example.com", "password123", UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        User user = userRepository.findByUsername("johndoe123");

        RestAssured.given()
                .pathParam("id", user.getId().toString())
                .when()
                .post("/admin/deactivateAccount/{id}")
                .then()
                .statusCode(204);

        user = userRepository.findByUsername("johndoe123");
        Assertions.assertFalse(user.isActive());

        RestAssured.given()
                .pathParam("id", user.getId().toString())
                .when()
                .post("/admin/activateAccount/{id}")
                .then()
                .statusCode(204);

        user = userRepository.findByUsername("johndoe123");
        Assertions.assertTrue(user.isActive());
    }

    @Test
    void changeUserRoleTest() {
        UserDTO userDTO = new UserDTO("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        User user = userRepository.findByUsername("tatuazyk123");

        RestAssured.given()
                .pathParam("id", user.getId().toString())
                .queryParam("userRole", "ADMIN")
                .when()
                .post("/admin/changeUserRole/{id}")
                .then()
                .statusCode(204);

        user = userRepository.findByUsername("tatuazyk123");
        Assertions.assertEquals(UserRole.ADMIN, user.getUserRole());
    }

    @Test
    void getAllUsersRoleTest() {
        UserDTO user1 = new UserDTO("Alice", "Smith", "alice123",
                "alice@example.com", "password123", UserRole.CLIENT);
        UserDTO user2 = new UserDTO("Charlie", "Brown", "charlie789",
                "charlie@example.com", "password789", UserRole.CLIENT);

        RestAssured.given()
                .body(user1)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        RestAssured.given()
                .body(user2)
                .contentType(ContentType.JSON)
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        RestAssured.given()
                .queryParam("userRole", "CLIENT")
                .when()
                .get("/admin/getAllUsersByRole")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].username", equalTo("alice123"))
                .body("[1].username", equalTo("charlie789"));
    }
}
