import api.CourierApi;
import io.qameta.allure.junit4.DisplayName;

import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.LoginData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import api.OrderApi;
import model.OrderData;

import static data.Constants.*;
import static org.hamcrest.CoreMatchers.*;


public class GetListOfOrdersTest {

    private ValidatableResponse response;
    private CourierApi courierApi;
    private OrderApi orderApi;
    private int orderId;
    private int courierId;

    @Before
    public void courierInit() {
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);

        courierApi = new CourierApi();
        CourierData courierData = new CourierData(login, password);
        courierApi.createCourier(courierData);
        LoginData loginData = new LoginData(login, password);
        response = courierApi.loginCourier(loginData);
        courierId = response.extract().body().jsonPath().getInt("id");
    }

    @After
    public void cleanUp() {
        response = courierApi.deleteCourier(courierId);
        response.log().all().assertThat().statusCode(HttpStatus.SC_OK);

        response = orderApi.cancelOrder(orderId);
        response.log().all().assertThat().statusCode(HttpStatus.SC_OK);
    }

    @DisplayName("Check can get a list of orders")
    @Test
    public void listOfOrderTest() {

        orderApi = new OrderApi();
        OrderData orderData = new OrderData(FIRST_NAME, LAST_NAME, ADDRESS, METRO_STATION, PHONE, RENT_TIME,
                DELIVERY_DATE, COMMENT, new String[]{});
        response = orderApi.createOrder(orderData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED);
        orderId = response.extract().body().jsonPath().getInt("track");

        response = orderApi.acceptOrder(orderId, courierId);
        response.log().all().statusCode(HttpStatus.SC_OK);

        response = orderApi.listOfOrder(courierId);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body("orders", notNullValue());
    }
}
