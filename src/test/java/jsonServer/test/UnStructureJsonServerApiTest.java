package jsonServer.test;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UnStructureJsonServerApiTest {
    @Test
    public void getPostsShouldSucceed() {
        given()
                .log().uri()
                .when()
                .get("http://localhost:3000/posts")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getPostDetailShouldSucceed() {
        given()
                .log().uri()
                .when()
                .get("http://localhost:3000/posts/1")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void createPostsShouldSucceed() {
        String json = "{\n" +
                "\"title\": \"a title11\",\n" +
                "\"views\": 300\n" +
                "}";
        given()
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .post("http://localhost:3000/posts")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    public void putPostsShouldSucceed() {
        String json = "{\n" +
                "\"title\": \"a title11\",\n" +
                "\"views\": 300\n" +
                "}";
        given()
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .put("http://localhost:3000/posts/1")
                .then()
                .statusCode(200)
                .log().body();
    }


    @Test
    public void patchPostsShouldSucceed() {
        String json = "{\n" +
                "\"title\": \"a title12\"\n" +
                "}";
        given()
                .header("Content-Type", "application/json")
                .body(json)
                .log().uri()
                .log().body()
                .when()
                .patch("http://localhost:3000/posts/1")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void deletePostsShouldSucceed() {

        given()
                .header("Content-Type", "application/json")
                .log().uri()
                .when()
                .delete("http://localhost:3000/posts/1")
                .then()
                .statusCode(200)
                .log().body();
    }
}
