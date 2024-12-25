import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.Constants.*;
import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private final OrderData orderData;
    private OrderApi orderApi;
    public ValidatableResponse response;

    @After
    public void cleanUp() {
        int orderId = response.extract().body().jsonPath().getInt("track");
        response = orderApi.cancelOrder(orderId);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("ok", is(true));
    }

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           int rentTime, String deliveryDate, String comment, String[] color) {
        orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    @Parameterized.Parameters (name = "firstName = {0}, lastName = {1}, address = {2}, metroStation = {3}, phone = {4}," +
            "rentTime = {5}, deliveryDate = {6}, comment = {7}, color = {8}")
    public static Object[][] getData() {
        return new Object[][] {
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT,
                        new String[]{BLACK_COLOR, GREY_COLOR}},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT,
                        new String[]{BLACK_COLOR}},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT,
                        new String[]{GREY_COLOR}},
                {FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME, DELIVERY_DATE, COMMENT,
                        new String[]{}}
        };
    }

    @DisplayName("Check order can be created")
    @Test
    public void orderCanBeCreatedTest() {

        orderApi = new OrderApi();
        response = orderApi.createOrder(orderData);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body(containsString("track"));
    }
}
