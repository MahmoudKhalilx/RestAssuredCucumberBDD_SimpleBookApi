package api.utils;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class ApiUtils {

    public static Response post(String endpoint, Object body) {
        return given()
                .contentType("application/json")
                .body(body)
                .post(endpoint);
    }

    public static Response get(String endpoint) {
        return given().get(endpoint);
    }

    public static Response getWithQuery(String endpoint, String key, String value) {
        return given()
                .queryParam(key, value)
                .when()
                .get(endpoint);
    }

    public static Response authPost(String endpoint, String token, Object body) {
        return given()
                .auth().oauth2(token)
                .contentType("application/json")
                .body(body)
                .post(endpoint);
    }

    public static Response authGet(String endpoint, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .get(endpoint);
    }

    public static Response authPatch(String endpoint, String token, Object body) {
        return given()
                .auth().oauth2(token)
                .contentType("application/json")
                .body(body)
                .patch(endpoint);
    }

    public static Response authDelete(String endpoint, String token) {
        return given()
                .auth().oauth2(token)
                .delete(endpoint);
    }
}
