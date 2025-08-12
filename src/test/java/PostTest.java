import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class PostTest {

    @Test
    public void getPostsShouldSucceed() {
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .log().body();

    }
}
