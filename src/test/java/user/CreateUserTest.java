package user;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static user.common.createUserJSON;

import org.json.simple.JSONObject;
import org.junit.Test;
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

        RequestSpecification request = given();
        Response response = request
                            .contentType("application/json")
                            .body(requestBody)
                            .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // check if created user exists
        assertEquals(200, get(BASEURL+userToCreate).andReturn().getStatusCode());
    }

    @Test
    public void createUserWithMissingUsernameDoesNotReturn200() {

        JSONObject requestBody = createUserJSON();
        requestBody.remove("username");

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .body(requestBody)
                .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertNotEquals(200, statusCode);
    }

    @Test
    public void createUserWithMissingPasswordDoesNotReturn200() {

        JSONObject requestBody = createUserJSON();
        requestBody.remove("password");

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .body(requestBody)
                .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertNotEquals(200, statusCode);
    }

    @Test
    public void createUserWithInvalidEmailDoesNotReturn200() {

        JSONObject requestBody = createUserJSON();
        requestBody.put("email", "invalidemail.com");

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .body(requestBody)
                .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertNotEquals(200, statusCode);
    }

    @Test
    public void createUserWithMissingRequestBodyDoesNotReturn200() {

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertNotEquals(200, statusCode);
    }

    @Test
    public void createUserWithInvalidBodyDoesNotReturn200() {

        JSONObject requestBody = new JSONObject();
        requestBody.put("invalid", "invalid");

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .body(requestBody)
                .post(BASEURL);

        int statusCode = response.getStatusCode();
        assertNotEquals(200, statusCode);
    }


    @Test
    public void createUserWithPutInsteadOfPostReturns405() {

        JSONObject requestBody = createUserJSON();

        RequestSpecification request = given();
        Response response = request
                .contentType("application/json")
                .body(requestBody)
                .put(BASEURL);

        int statusCode = response.getStatusCode();
        assertEquals(405, statusCode);

    }
}