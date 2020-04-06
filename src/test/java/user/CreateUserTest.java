package user;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static user.common.createUserJSON;

import org.json.simple.JSONObject;
import petstore.PetStoreTest;

public class CreateUserTest extends PetStoreTest {

    public static final String BASEURL=getProperty("baseurl")+"/user/";

    @Test
    public void createUserReturns200() {

        String userToCreate = "userToCreate";

        // delete user if already exists
        delete(BASEURL+userToCreate);

        // create user
        JSONObject requestBody = createUserJSON();
        requestBody.put("username", userToCreate);

        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post(BASEURL)
            .then()
                .assertThat().statusCode(200);

        // check that new user exists
        given()
            .contentType("application/json")
            .when()
                .get(BASEURL+userToCreate)
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
                .post(BASEURL)
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
                .post(BASEURL)
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
                .post(BASEURL)
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithMissingRequestBodyReturns4xx() {

        given()
            .contentType("application/json")
            .when()
                .post(BASEURL)
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }

    @Test
    public void createUserWithInvalidBodyReturns4xx() {

        JSONObject requestBody = new JSONObject();
        requestBody.put("invalid", "invalid");

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(BASEURL)
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
                .put(BASEURL)
            .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }
}