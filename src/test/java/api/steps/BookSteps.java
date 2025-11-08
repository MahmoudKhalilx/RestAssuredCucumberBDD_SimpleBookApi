package api.steps;

import api.pojo.*;
import api.utils.ApiUtils;
import api.utils.TestDataReader;
import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

public class BookSteps {

    public static String token;
    public static int savedBookId;
    public static String savedOrderId;

    Response response;
    String email = "tester" + System.currentTimeMillis() + "@test.com";
    Map<String, Object> data = TestDataReader.readJson("src/test/resources/testData.json");



    @Given("I create a new client and get access token")
    public void createClient() {

        ClientRequest request = new ClientRequest(
                data.get("clientName").toString(),
                email
        );
        System.out.println("User Name: "+ request.getClientName() + "\nEmail: " + request.getClientEmail());
        response = ApiUtils.post("/api-clients/", request);

        ClientResponse resp = response.as(ClientResponse.class);

        if (resp.getError() != null) {
            throw new RuntimeException("API returned error: " + resp.getError());
        }
        token = resp.getAccessToken();
        Assert.assertNotNull(token, "Access token must not be null");

    }


    @When("I send a GET request to fetch {string} books")
    public void iSendAGetRequestToFetchBooks(String type) {
        response = ApiUtils.getWithQuery("/books", "type", type);

        Book[] books = response.as(Book[].class);

        int availableId = -1;

        for (Book book : books) {
            if (book.isAvailable()) {
                availableId = book.getId();
                break;
            }
        }
        if (availableId == -1) {
            throw new RuntimeException("No available books found for this type");
        }
        savedBookId = availableId;
        Assert.assertTrue(savedBookId > 0, "Book ID should be > 0");
    }

    @When("I get the book details")
    public void getBookDetails() {
        response = ApiUtils.get("/books/" + savedBookId);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @When("I create a new order")
    public void createOrder() {
        OrderRequest req = new OrderRequest(savedBookId, data.get("clientName").toString());
        response = ApiUtils.authPost("/orders", token, req);
        OrderResponse resp = response.as(OrderResponse.class);
        savedOrderId = resp.getOrderId();
        Assert.assertNotNull(savedOrderId);
    }

    @Then("I get all my orders")
    public void getAllOrders() {
        response = ApiUtils.authGet("/orders", token);
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Then("I get the order details")
    public void getOrderDetails() {
        response = ApiUtils.authGet("/orders/" + savedOrderId, token);
        response.prettyPrint();
        Assert.assertEquals(response.statusCode(), 200);
    }

    @Then("I update the order name")
    public void updateOrder() {
        OrderRequest req = new OrderRequest(savedBookId,data.get("UpdatedClientName").toString());
        response = ApiUtils.authPatch("/orders/" + savedOrderId, token, req);
        Assert.assertEquals(response.statusCode(), 204);
    }

    @Then("I delete the order")
    public void deleteOrder() {
        response = ApiUtils.authDelete("/orders/" + savedOrderId, token);
        Assert.assertEquals(response.statusCode(), 204);
    }
}
