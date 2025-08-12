package click.simple_grocery_store.api;

import com.thedeanda.lorem.LoremIpsum;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

// https://github.com/vdespa/Postman-Complete-Guide-API-Testing/blob/main/simple-grocery-store-api.md

public class BaseGroceryStoreApiTest {

    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri("https://simple-grocery-store-api.click")
                .setContentType(ContentType.JSON)
                .build();
    }

    public RequestSpecification requestSpecForPrivateApi() {
        return new RequestSpecBuilder()
                .addRequestSpecification(requestSpec())
//                .addHeader("Authorization", "Bearer " + getBearerToken())
                .addHeader("Authorization", "Bearer " + "a443a6799b934d1b49a05d495e3271ebf1e627350c9ba04eafc0d9a1bd9b82e8")
                .build();
    }

    public String getCartId() {
        return given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .statusCode(201)
                .log().body()
                .extract().jsonPath().getString("cartId");
    }

    public String getBearerToken() {
        Map<String, String> clientJson = new HashMap<>();
        clientJson.put("clientName", LoremIpsum.getInstance().getTitle(4));
        clientJson.put("clientEmail", LoremIpsum.getInstance().getEmail());

        return given()
                .spec(requestSpec())

                .body(clientJson)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .extract().jsonPath().getString("accessToken");
    }

    public String getOrderId() {
        return given()
                .spec(requestSpecForPrivateApi())
                .log().uri()
                .when()
                .get("/orders")
                .then()
                .statusCode(200)
                .extract().jsonPath().getString("[0].id");
    }
}
