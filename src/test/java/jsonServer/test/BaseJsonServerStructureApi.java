package jsonServer.test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseJsonServerStructureApi {
    private final static String BASE_URL = "http://localhost";

    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                .build();
    }

    public Map<String, Object> getPostJson(String title, int views) {
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("title", title);
        postJson.put("views", views);
        return postJson;
    }

    public Map<String, Object> getPostJson(String title) {
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("title", title);
        return postJson;
    }

    public Map<String, Object> getPostJson(int views) {
        Map<String, Object> postJson = new HashMap<>();
        postJson.put("views", views);
        return postJson;
    }

    public String getPostId(){
        return given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .when()
                .get("posts")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");
    }
}
