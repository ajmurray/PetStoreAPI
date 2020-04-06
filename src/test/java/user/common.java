package user;

import org.json.simple.JSONObject;

public class common {

    public static JSONObject createUserJSON() {
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
}
