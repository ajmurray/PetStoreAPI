import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.json.simple.JSONObject;

public class PetStoreTest {

    public JSONObject createUserJSON() {
        JSONObject requestBody = new JSONObject();

        requestBody.put("id", 0);
        requestBody.put("username", "uname");
        requestBody.put("firstName", "fname");
        requestBody.put("lastName", "lname");
        requestBody.put("email", "abc@email.com");
        requestBody.put("password", "passwd");
        requestBody.put("phone", "+37255555555");
        requestBody.put("userStatus", 0);

        return requestBody;
    }

    @Test
    public void createUserReturns200() {

        String userToCreate = "userToCreate";

        // delete user if already exists
        delete("https://petstore.swagger.io/v2/user/"+userToCreate);

        // create user
        JSONObject requestBody = createUserJSON();
        requestBody.put("username", userToCreate);

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().statusCode(200);

        // check that new user exists
        given()
            .contentType("application/json")
            .when()
                .get("https://petstore.swagger.io/v2/user/"+userToCreate)
            .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void createUserWithMissingUsernameReturns4xx() {

        JSONObject requestBody = createUserJSON();
        requestBody.remove("username");

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithMissingPasswordReturns4xx() {

        JSONObject requestBody = createUserJSON();
        requestBody.remove("password");

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithInvalidEmailReturns4xx() {

        JSONObject requestBody = createUserJSON();
        requestBody.put("email", "invalidemail.com");

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithMissingRequestBodyReturns4xx() {

        given()
            .contentType("application/json")
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithPutInsteadOfPostReturns4xx() {

        JSONObject requestBody = createUserJSON();

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .put("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void deleteUserReturns200() {

        // first create user to delete
        JSONObject requestBody = createUserJSON();
        String userToDelete = "toDelete";
        requestBody.put("username", userToDelete);

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://petstore.swagger.io/v2/user")
            .then()
                .assertThat().statusCode(200);

        // delete user
        given()
            .contentType("application/json")
            .when()
                .delete("https://petstore.swagger.io/v2/user/"+userToDelete)
            .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void deleteUserNotFoundReturns404() {

        given()
            .contentType("application/json")
            .when()
                .delete("https://petstore.swagger.io/v2/user/"+"invalid111111")
            .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void deleteWithNoUserSpecifiedReturns4xx() {

        given()
            .contentType("application/json")
            .when()
                .delete("https://petstore.swagger.io/v2/user/")
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }
}