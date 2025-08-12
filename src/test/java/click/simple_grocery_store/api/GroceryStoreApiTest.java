package click.simple_grocery_store.api;

import com.thedeanda.lorem.LoremIpsum;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

// https://github.com/vdespa/Postman-Complete-Guide-API-Testing/blob/main/simple-grocery-store-api.md
public class GroceryStoreApiTest extends BaseGroceryStoreApiTest {
    @Test
    public void getProductsShouldSucceed() {
        given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void getProductDetailShouldSucceed() {
        int productId = given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
//                .log().body()
                .extract().jsonPath().getInt("[0].id");

        given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products/" + productId)
//                .get("/products/{{productId}}", productId)
                .then()
                .statusCode(200)
                .log().body()
                .body("id", equalTo(productId));
    }

    @Test
    public void getWrongProductDetailShouldFail() {
        given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products/5646546")
                .then()
                .statusCode(404)
                .log().body()
                .body("error", equalTo("No product with id 5646546."));
    }

    @Test
    public void createCartShouldSucceed() {
        given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .post("/carts")
                .then()
                .statusCode(201)
                .log().body();
    }

    @Test
    public void getCartShouldSucceed() {
        given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/carts/" + getCartId())
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void addItemToCartShouldSucceed() {
        int productId = given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        Map<String, Integer> projectJson = new HashMap<>();
        projectJson.put("productId", productId);
        projectJson.put("quantity", 2);

        given()
                .spec(requestSpec())
                .log().uri()
                .body(projectJson)
                .when()
                .post("/carts/" + getCartId() + "/items")
                .then()
                .statusCode(201)
                .log().body();
    }


    @Test
    public void createOrderShouldSucceed() {
        int productId = given()
                .spec(requestSpec())
                .log().uri()
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract().jsonPath().getInt("[0].id");

        String cartId = getCartId();
        Map<String, Integer> projectJson = new HashMap<>();
        projectJson.put("productId", productId);
        projectJson.put("quantity", 5);

        given()
                .spec(requestSpec())
                .log().uri()
                .body(projectJson)
                .when()
                .post("/carts/" + cartId + "/items")
                .then()
                .statusCode(201)
                .log().body();

        Map<String, Object> orderJson = new HashMap<>();
        orderJson.put("cartId", cartId);
        orderJson.put("customerName", LoremIpsum.getInstance().getName());
        orderJson.put("comment", LoremIpsum.getInstance().getTitle(10));

        given()
                .spec(requestSpecForPrivateApi())
                .body(orderJson)
                .log().uri()
                .log().body()
                .when()
                .post("/orders")
                .then()
//                .statusCode(201)
                .log().body();
    }


    @Test
    public void getOrdersShouldSucceed() {
        getOrderId();
    }

    @Test
    public void getOrderDetailShouldSucceed() {

        given()
                .spec(requestSpecForPrivateApi())
                .log().uri()
                .when()
                .get("/orders/" + getOrderId())
                .then()
                .statusCode(200)
                .log().body();
    }

    @Test
    public void updateOrderShouldSucceed() {
        Map<String, Object> orderJson = new HashMap<>();
        orderJson.put("customerName", LoremIpsum.getInstance().getName());
        orderJson.put("comment", LoremIpsum.getInstance().getTitle(10));

        given()
                .spec(requestSpecForPrivateApi())
                .body(orderJson)
                .log().uri()
                .log().body()
                .when()
                .patch("/orders/" + getOrderId())
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void deleteOrderShouldSucceed() {
        given()
                .spec(requestSpecForPrivateApi())
                .log().uri()
                .when()
                .delete("/orders/" + getOrderId())
                .then()
                .log().body()
                .statusCode(204);
    }

    @Test
    public void registerNewApiClientShouldSucceed() {
        Map<String, String> clientJson = new HashMap<>();
        clientJson.put("clientName", LoremIpsum.getInstance().getTitle(4));
        clientJson.put("clientEmail", LoremIpsum.getInstance().getEmail());

        given()
                .spec(requestSpec())

                .body(clientJson)
                .log().uri()
                .log().body()
                .when()
                .post("/api-clients")
                .then()
                .statusCode(201)
                .log().body();
    }

}
