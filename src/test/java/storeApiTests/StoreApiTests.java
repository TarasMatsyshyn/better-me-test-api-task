package storeApiTests;

import api.clients.StoreClient;
import api.models.InventoryResponse;
import api.models.OrderRequest;
import api.models.OrderResponse;
import baseTest.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertNotNull;

public class StoreApiTests extends BaseTest {

    //Inventory tests
    @Test
    public void testGetInventoryHappyFlow() {
        InventoryResponse inventory = StoreClient.getInventory();

        assertNotNull(inventory.getInventory().get("sold"));
        assertNotNull(inventory.getInventory().get("available"));
        assertNotNull(inventory.getInventory().get("pending"));

        assertTrue(inventory.getInventory().containsKey("sold"));
        assertTrue(inventory.getInventory().containsKey("available"));
        assertTrue(inventory.getInventory().containsKey("pending"));

        assertTrue(inventory.getInventory().get("sold") >= 0);
        assertTrue(inventory.getInventory().get("available") >= 0);
        assertTrue(inventory.getInventory().get("pending") >= 0);
    }

    //Create order tests
    @Test
    public void createOrderTestHappyFlow() {
        OrderRequest orderRequest = generateOrderRequest();
        OrderResponse orderResponse = StoreClient.createOrder(orderRequest);

        assertThat(orderResponse.getStatus()).isNotNull();
        assertThat(orderResponse.getId()).isEqualTo(orderRequest.getId());
        assertThat(orderResponse.getPetId()).isEqualTo(orderRequest.getPetId());
        assertThat(orderResponse.getQuantity()).isEqualTo(orderRequest.getQuantity());
        assertThat(orderResponse.getShipDate()).isEqualTo(orderRequest.getShipDate());
        assertThat(orderResponse.getStatus()).isEqualTo(orderRequest.getStatus());
        assertThat(orderResponse.isComplete()).isEqualTo(orderRequest.isComplete());
    }

    @Test
    public void createInvalidOrderTest() {
        OrderRequest orderRequest = generateInvalidRequest();
        Response orderResponse = StoreClient.createInvalidOrder(orderRequest);

        assertThat(orderResponse.getStatusCode()).isEqualTo(500);
    }

    //Get order tests
    @Test
    public void testGetOrderHappyFlow() {
        OrderRequest orderRequest = generateOrderRequest();

        OrderResponse orderResponse = StoreClient.getOrder(1);

        assertThat(orderResponse).isNotNull();
        assertThat(orderResponse.getId()).isEqualTo(orderRequest.getId());
        assertThat(orderResponse.getPetId()).isEqualTo(orderRequest.getPetId());
        assertThat(orderResponse.getQuantity()).isEqualTo(orderRequest.getId());
        assertThat(orderResponse.getShipDate()).isEqualTo(orderRequest.getShipDate());
        assertThat(orderResponse.getStatus()).isEqualTo(orderRequest.getStatus());
        assertThat(orderResponse.isComplete()).isEqualTo(orderRequest.isComplete());
    }

    //Delete tests
    @Test
    public void testDeleteOrderHappyFlow() {
        OrderRequest orderRequest = generateOrderRequest();

        StoreClient.createOrder(orderRequest);
        assertThat(StoreClient.deleteOrder(1)).isEqualTo(200);
    }

    @Test
    public void testDeleteOrderNegative() {
        assertThat(StoreClient.deleteOrder(0)).isEqualTo(404);
    }

    //Helpers methods
    private OrderRequest generateOrderRequest(){
        OrderRequest orderRequest = OrderRequest.builder()
                .id(1)
                .petId(0)
                .quantity(0)
                .shipDate("2025-03-03T15:08:43.718Z")
                .status("placed")
                .complete(true)
                .build();
        return orderRequest;
    }

    private OrderRequest generateInvalidRequest(){
        OrderRequest orderRequest = OrderRequest.builder()
                .id(1)
                .petId(0)
                .quantity(0)
                .shipDate("Invalid date")
                .status("placed")
                .complete(true)
                .build();
        return orderRequest;
    }

}
