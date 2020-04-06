package user;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static user.common.createUserJSON;

import org.json.simple.JSONObject;
import petstore.PetStoreTest;

public class DeleteUserTest extends PetStoreTest {

    public static final String BASEURL=getProperty("baseurl")+"/user/";

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
                .post(BASEURL)
                .then()
                .assertThat().statusCode(200);

        // delete user
        given()
                .contentType("application/json")
                .when()
                .delete(BASEURL+userToDelete)
                .then()
                .assertThat().statusCode(200);
    }

    @Test
    public void deleteUserNotFoundReturns404() {

        given()
                .contentType("application/json")
                .when()
                .delete(BASEURL+"invalid111111")
                .then()
                .assertThat().statusCode(404);
    }

    @Test
    public void deleteWithNoUserSpecifiedReturns4xx() {

        given()
                .contentType("application/json")
                .when()
                .delete(BASEURL)
                .then()
                .assertThat().body("code", greaterThanOrEqualTo(400));
    }
}
