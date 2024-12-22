import io.qameta.allure.junit4.DisplayName;
import model.CourierData;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest extends BaseApiTest{

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        response = courierApi.createCourier(courierData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @DisplayName("Check the same courier can't be created")
    @Test
    public void sameCourierCantBeCreatedTest() {
        courierData = new CourierData(login, password, firstName);

        response = courierApi.createCourier(courierData);
        response = courierApi.createCourier(courierData);
        response.log().all()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .assertThat()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Check courier can't be created without login")
    @Test
    public void cantCreateCourierWithoutLoginTest() {
        courierData = new CourierData(null, password, firstName);
        response = courierApi.createCourier(courierData);

        response.log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @DisplayName("Check courier can't be created without password")
    @Test
    public void cantCreateCourierWithoutPasswordTest() {
        courierData = new CourierData(login, null);
        response = courierApi.createCourier(courierData);

        response.log().all()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .assertThat()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
