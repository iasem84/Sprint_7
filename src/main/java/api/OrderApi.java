package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CancelOrderData;
import model.OrderData;

import static io.restassured.RestAssured.given;

public class OrderApi extends RestApi {

    public static final String CREATE_ORDER_URI = "/api/v1/orders";
    public static final String ACCEPT_ORDER_URI = "/api/v1/orders/accept/%d?courierId=%d";
    public static final String CANCEL_ORDER_URI = "/api/v1/orders/cancel";
    public static final String LIST_ORDER_URI = "/api/v1/orders?courierId=%d";

    @Step("Create order")
    public ValidatableResponse createOrder(OrderData order){
        return given()
                .spec(requestSpecification())
                .and()
                .body(order)
                .when()
                .post(CREATE_ORDER_URI)
                .then();
    }

    @Step("Accept order")
    public ValidatableResponse acceptOrder(int courierId, int orderId){
        return given()
                .spec(requestSpecification())
                .and()
                .put(String.format(ACCEPT_ORDER_URI, courierId, orderId))
                .then();
    }

    @Step("Cancel order")
    public ValidatableResponse cancelOrder(CancelOrderData cancelOrderData){
        return given()
                .spec(requestSpecification())
                .and()
                .body(cancelOrderData)
                .when()
                .put(CANCEL_ORDER_URI)
                .then();
    }

    @Step("Get list of orders")
    public ValidatableResponse listOfOrder(int courierId){
        return given()
                .spec(requestSpecification())
                .and()
                .get(String.format(LIST_ORDER_URI, courierId))
                .then();
    }
}
