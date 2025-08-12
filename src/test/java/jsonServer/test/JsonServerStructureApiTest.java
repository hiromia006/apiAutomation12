package jsonServer.test;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class JsonServerStructureApiTest extends BaseJsonServerStructureApi {
    @Test
    public void getPostsShouldSucceed() {
        given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .when()
                .get("posts")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void detailPostShouldSucceed() {
        given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .log().body()
                .when()
                .get("/posts/" + getPostId())
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    public void detailPostWithInvalidIdShouldFail() {
        given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .log().body()
                .when()
                .get("/posts/asdsad")
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    public void createPostsShouldSucceed() {
        given()
                .spec(requestSpec())
                .body(getPostJson(LoremIpsum.getInstance().getTitle(3), 200))
                .port(3000)
                .log().uri()
                .log().body()
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    public void createPostsWithAssertionShouldSucceed() {
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();
        given()
                .spec(requestSpec())
                .body(getPostJson(title, views))
                .port(3000)
                .log().uri()
                .log().body()
                .when()
                .post("posts")
                .then()
                .statusCode(201)
                .log().body()
                .body("title", equalTo(title))
                .body("views", equalTo(views));
    }

    @Test
    public void putPostsShouldSucceed() {
        String title = LoremIpsum.getInstance().getTitle(3);
        int views = new Random().nextInt();
        given()
                .spec(requestSpec())
                .port(3000)
                .body(getPostJson(title, views))
                .log().uri()
                .log().body()
                .when()
                .put("/posts/" + getPostId())
                .then()
                .statusCode(200)
                .log().body()
                .body("title", equalTo(title))
                .body("views", equalTo(views))
                .body("id", notNullValue());
        ;
    }

    @Test
    public void patchPostsShouldSucceed1() {
        given()
                .spec(requestSpec())
                .port(3000)
                .body(getPostJson(LoremIpsum.getInstance().getTitle(4)))
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/" + getPostId())
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void patchPostsShouldSucceed2() {

        given()
                .spec(requestSpec())
                .port(3000)
                .body(getPostJson(400))
                .log().uri()
                .log().body()
                .when()
                .patch("/posts/" + getPostId())
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void deletePostShouldSucceed() {

        given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .when()
                .delete("/posts/" + getPostId())
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void deletePostShouldFail() {

        given()
                .spec(requestSpec())
                .port(3000)
                .log().uri()
                .when()
                .delete("/posts/4ds6as")
                .then()
                .statusCode(404)
                .log().body();
    }
}
