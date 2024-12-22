import api.OrderApi;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.OrderData;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTest{

    protected OrderData orderData;
    protected OrderApi orderApi;
    protected ValidatableResponse response;

    private static final String BLACK_COLOR = "BLACK";
    private static final String GREY_COLOR = "GREY";

//    @After
//    public void cleanUp() {
//        LoginResponseData loginResponse = courierApi.loginCourier(new LoginData(login, password))
//                .extract().as(LoginResponseData.class);
//        id = loginResponse.getId();
//        if (id != 0) {
//            response = courierApi.deleteCourier(id);
//        }
//    }

    public CreateOrderTest(String firstName, String lastName, String address, String metroStation, String phone,
                           int rentTime, String deliveryDate, String comment, String[] color) {
        orderData = new OrderData(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
    }

    @Parameterized.Parameters
    public static Object[][] getData() {
        return new Object[][] {
                {"Asem", "Ким", "Толе би 298/1", "Белорусская", "87012170000", 3, "2024-12-30", "лололо",
                        new String[]{BLACK_COLOR, GREY_COLOR}},
                {"Asem", "Ким", "Толе би 298/1", "Белорусская", "87012170000", 3, "2024-12-30", "лололо",
                        new String[]{BLACK_COLOR}},
                {"Asem", "Ким", "Толе би 298/1", "Белорусская", "87012170000", 3, "2024-12-30", "лололо",
                        new String[]{GREY_COLOR}},
                {"Asem", "Ким", "Толе би 298/1", "Белорусская", "87012170000", 3, "2024-12-30", "лололо",
                        new String[]{}}
        };
    }

    @DisplayName("Check order can be created")
    @Test
    public void orderCanBeCreatedTest() {

        orderApi = new OrderApi();
        //вызываем метод
        response = orderApi.createOrder(orderData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .body(containsString("track"));
        int track = response.extract().body().jsonPath().getInt("track");
    }
}
