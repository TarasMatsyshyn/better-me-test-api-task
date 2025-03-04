package api.clients;

import api.models.InventoryResponse;
import api.models.OrderRequest;
import api.models.OrderResponse;
import lombok.extern.slf4j.Slf4j;
import io.restassured.response.Response;

import static api.endpoints.Endpoints.INVENTORY;
import static api.endpoints.Endpoints.ORDER;
import static io.restassured.RestAssured.given;

@Slf4j
public class StoreClient {

    protected final static String BASE_URL = "https://petstore.swagger.io/v2";

    public static InventoryResponse getInventory() {
        log.info("Trying to GET inventory");
        return given()
                .baseUri(BASE_URL)
                .when()
                .get(INVENTORY)
                .then().log().all()
                .extract().body().as(InventoryResponse.class);
    }

    public static OrderResponse createOrder(OrderRequest orderRequest) {
        log.info("Trying to CREATE new order");
        OrderResponse response = given()
                .body(orderRequest)
                .post(ORDER)
                .then().log().all()
                .extract().body().as(OrderResponse.class);
        return response;
    }

    public static Response createInvalidOrder(OrderRequest orderRequest) {
        log.info("Trying to CREATE INVALID order");
        Response response = given()
                .body(orderRequest)
                .post(ORDER)
                .then().log().all()
                .extract().response();
        return response;
    }

    public static OrderResponse getOrder(int orderId) {
        log.info("Trying to GET order ID: " + orderId);
        OrderResponse response = given()
                .when()
                .get(ORDER + "/" + orderId)
                .then().log().all()
                .extract().body().as(OrderResponse.class);
        return response;
    }

    public static int deleteOrder(int orderId) {
        log.info("Trying to DELETE order ID: " + orderId);
        Response response = given()
                .when()
                .delete(ORDER + "/" + orderId)
                .then().log().all()
                .extract().response();
        return response.getStatusCode();
    }
}
