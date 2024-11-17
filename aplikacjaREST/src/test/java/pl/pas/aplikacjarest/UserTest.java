package pl.pas.aplikacjarest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pl.pas.aplikacjarest.dto.admin.AdminCreateDTO;
import pl.pas.aplikacjarest.dto.client.ClientCreateDTO;
import pl.pas.aplikacjarest.dto.manager.ManagerCreateDTO;

import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
    }

    @Test
    void registerClientPositiveTest() throws JSONException {
        ClientCreateDTO clientCreateDTO = new ClientCreateDTO("Jadwiga",
                "Hymel", "jhymel", "jadwigahymel@example.com", "synaniemawdomu");
        ManagerCreateDTO managerCreateDTO = new ManagerCreateDTO("John", "Bug",
                "JBuggy123", "johnbug@example.com", "akjfhasksdgf");
        AdminCreateDTO adminCreateDTO = new AdminCreateDTO("Sebasitan", "Alvarez",
                "tatuazyk123", "sentino@example.com", "sdagfajadf");

//        JSONObject json = new JSONObject();
//        json.put("firstName", "Jadwiga");
//        json.put("lastName", "Hymel");
//        json.put("username", "jhymel");
//        json.put("email", "jadwigahymel@example.com");
//        json.put("password", "securepassword123");
//        json.put("clientType","Default");


        RestAssured.given()
                .body(clientCreateDTO)
                .contentType("application/json")
                .when()
                .post("/client/register")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("firstName", equalTo("Jadwiga"))
                .body("lastName", equalTo("Hymel"))
                .body("username", equalTo("jhymel"))
                .body("email", equalTo("jadwigahymel@example.com"))
                .body("clientType", equalTo("Default"));
    }




}
