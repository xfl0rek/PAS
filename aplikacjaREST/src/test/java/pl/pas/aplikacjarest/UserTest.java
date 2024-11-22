package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.LoginDTO;
import pl.pas.aplikacjarest.dto.UserDTO;
import pl.pas.aplikacjarest.model.Admin;
import pl.pas.aplikacjarest.model.Client;
import pl.pas.aplikacjarest.model.Manager;
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
        RestAssured.basePath = "/api/users";
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

        Assertions.assertNotNull(userRepository.findByUsername("jhymel"));

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
    void getClientByUsernameTest() {
        Client client = new Client("John", "Bug", "JBuggy",
                "jbug@example.com", "123456789");
        userRepository.save(client);

        RestAssured.given()
                .queryParam("username", "JBuggy")
                .when()
                .get("/getUser")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Bug"))
                .body("username", equalTo("JBuggy"))
                .body("email", equalTo("jbug@example.com"))
                .body("password", equalTo("123456789"))
                .body("userRole", equalTo("CLIENT"));
    }

    @Test
    void getUsersByPartialUsernameTest() {
        Client client1 = new Client("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789");
        Client client2 = new Client("Jadwiga", "Hymel", "jhymel", "j.hymel@gmail.com", "strongpassword");
        Client client3 = new Client("John", "Bug", "jbuggy", "jbug@example.bug", "234726385");
        userRepository.save(client1);
        userRepository.save(client2);
        userRepository.save(client3);

        RestAssured.given()
                .queryParam("partialUsername", "j")
                .when()
                .get("/getUsersByPartialUsername")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].firstName", equalTo("Jadwiga"))
                .body("[0].username", equalTo("jhymel"))
                .body("[1].firstName", equalTo("John"))
                .body("[1].username", equalTo("jbuggy"));
    }

    @Test
    void activateAndDeactivateAccountTest() {
        Client client = new Client("John", "Doe", "johndoe123",
                "john.doe@example.com", "password123");
        ObjectId clientID = userRepository.save(client);

        Assertions.assertTrue(client.isActive());

        RestAssured.given()
                .pathParam("id", clientID.toString())
                .when()
                .post("/deactivateAccount/{id}")
                .then()
                .statusCode(204);

        Assertions.assertFalse(userRepository.findByUsername("johndoe123").isActive());

        RestAssured.given()
                .pathParam("id", clientID.toString())
                .when()
                .post("/activateAccount/{id}")
                .then()
                .statusCode(204);

        Assertions.assertTrue(userRepository.findByUsername("johndoe123").isActive());
    }

    @Test
    void changeUserRoleTest() {
        Client client = new Client("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789");
        ObjectId clientID = userRepository.save(client);

        RestAssured.given()
                .pathParam("id", clientID.toString())
                .queryParam("userRole", "ADMIN")
                .when()
                .post("/changeUserRole/{id}")
                .then()
                .statusCode(204);

        Assertions.assertEquals(UserRole.ADMIN, userRepository.findByUsername("tatuazyk123").getUserRole());
    }

//    @Test
//    void updateClientPositiveTest() {
//        Client client1 = new Client("Alice", "Smith", "alice123",
//                "alice@example.com", "password123");
//        ObjectId clientID = userRepository.save(client1);
//        client1.setId(clientID);
//        userRepository.update(client1);
//
//        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
//                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
//
//        RestAssured.given()
//                .pathParam("id", clientID.toString())
//                .body(userDTO)
//                .contentType("application/json")
//                .when()
//                .put("/updateUser/{id}")
//                .then()
//                .statusCode(204);
//
//        User updatedUser = userRepository.findByID(clientID);
//        Assertions.assertEquals("Jadwiga", updatedUser.getFirstName());
//        Assertions.assertEquals("Hymel", updatedUser.getLastName());
//        Assertions.assertEquals("jhymel", updatedUser.getUsername());
//        Assertions.assertEquals("jadwigahymel@example.com", updatedUser.getEmail());
//
//    }

    @Test
    void getAllUsersRoleTest() {
        Client client1 = new Client("Alice", "Smith", "alice123",
                "alice@example.com", "password123");
        Client client2 = new Client("Charlie", "Brown", "charlie789",
                "charlie@example.com", "password789");
        userRepository.save(client1);
        userRepository.save(client2);

        RestAssured.given()
                .queryParam("userRole", "CLIENT")
                .when()
                .get("/getAllUsersByRole")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(2))
                .body("[0].username", equalTo("alice123"))
                .body("[1].username", equalTo("charlie789"));
    }

    @Test
    void getAllUsersPositiveTest() {
        Client client1 = new Client("Alice", "Smith", "alice123",
                "alice@example.com", "password123");
        Client client2 = new Client("Charlie", "Brown", "charlie789",
                "charlie@example.com", "password789");
        Manager manager = new Manager("John", "Doe", "johndoe123",
                "John@example.com", "password123");
        Admin admin = new Admin("Jane", "Doe", "janedoe123",
                "jane@example.com", "password123");

        userRepository.save(client1);
        userRepository.save(client2);
        userRepository.save(manager);
        userRepository.save(admin);

        RestAssured.given()
                .when()
                .get("/")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("$.size()", equalTo(4))
                .body("[0].username", equalTo("alice123"))
                .body("[1].username", equalTo("charlie789"))
                .body("[2].username", equalTo("johndoe123"))
                .body("[3].username", equalTo("janedoe123"));
    }

    @Test
    void getUserByIDTest() {
        Client client = new Client("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789");
        ObjectId clientID = userRepository.save(client);

        RestAssured.given()
                .pathParam("id", clientID.toString())
                .get("/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Sebastian"))
                .body("lastName", equalTo("Alvarez"))
                .body("username", equalTo("tatuazyk123"))
                .body("email", equalTo("sentino@example.com"))
                .body("password", equalTo("123456789"))
                .body("userRole", equalTo("CLIENT"));
    }

    //Testy negatywne

    @Test
    void invalidArgumentsPassedTest() {
        UserDTO userDTO = new UserDTO("Se", "", "tatuazyk123",
                "notanemail", "12789" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(400);

        Assertions.assertNull(userRepository.findByUsername("tatuazyk123"));
    }

    @Test
    void loginUserInvalidPasswordNegativeTest() {
         Client client = new Client("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);

        LoginDTO wrongLoginDTO = new LoginDTO("jhymel", "1234566745");
        RestAssured.given()
                .body(wrongLoginDTO)
                .contentType(ContentType.JSON)
                .when()
                .post("/login")
                .then()
                .statusCode(400);
    }

    @Test
    void registerClientWithUsedUsernameNegativeTest() {
        UserDTO userDTO = new UserDTO("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu" , UserRole.CLIENT);
        RestAssured.given()
                .body(userDTO)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(201);

        UserDTO userDTOWithUsedUsername = new UserDTO("John", "Bug", "jhymel",
                    "john.bug@gmail.com", "12345678", UserRole.CLIENT);
        RestAssured.given()
                .body(userDTOWithUsedUsername)
                .contentType("application/json")
                .when()
                .post("/register")
                .then()
                .statusCode(400);
    }

    @Test
    void getClientByWrongUsernameNegativeTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu");
        userRepository.save(client);

        RestAssured.given()
                .queryParam("username", "jadwigahymel")
                .when()
                .get("/getUser")
                .then()
                .statusCode(404);
    }

    @Test
    void deactivateAndActivateAccountNegativeTest() {
        Client client = new Client("Jadwiga", "Hymel", "jhymel",
                "jadwigahymel@example.com", "synaniemawdomu");
        ObjectId clientID = userRepository.save(client);

        Assertions.assertTrue(client.isActive());

        RestAssured.given()
                .pathParam("id", "zleid")
                .when()
                .post("/deactivateAccount/{id}")
                .then()
                .statusCode(500);

        Assertions.assertTrue(userRepository.findByID(clientID).isActive());

        RestAssured.given()
                .pathParam("id", clientID.toString())
                .when()
                .post("/deactivateAccount/{id}")
                .then()
                .statusCode(204);

        Assertions.assertFalse(userRepository.findByID(clientID).isActive());

        RestAssured.given()
                .pathParam("id", "zleid")
                .when()
                .post("/activateAccount/{id}")
                .then()
                .statusCode(500);

        Assertions.assertFalse(userRepository.findByID(clientID).isActive());
    }

    @Test
    void changeUserRoleNegativeTest() {
        Client client = new Client("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789");
        ObjectId clientID = userRepository.save(client);

        RestAssured.given()
                .pathParam("id", "zleid")
                .queryParam("userRole", "ADMIN")
                .when()
                .post("/changeUserRole/{id}")
                .then()
                .statusCode(500);

        Assertions.assertEquals(UserRole.CLIENT, userRepository.findByID(clientID).getUserRole());
    }

    @Test
    void getUserByIDNegativeTest() {
        Client client = new Client("Sebastian", "Alvarez", "tatuazyk123",
                "sentino@example.com", "123456789");
        userRepository.save(client);

        RestAssured.given()
                .pathParam("id", "zleid")
                .get("/{id}")
                .then()
                .statusCode(500);
    }
}
