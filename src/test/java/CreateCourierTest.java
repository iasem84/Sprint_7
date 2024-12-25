import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class CreateCourierTest extends BaseApiTest{

    @DisplayName("Check courier can be created")
    @Test
    public void courierCanBeCreatedTest() {

        createCourier(login, password, firstName);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CREATED)
                .body("ok", is(true));
    }

    @DisplayName("Check the same courier can't be created")
    @Test
    public void sameCourierCantBeCreatedTest() {

        createCourier(login, password, firstName);
        response = courierApi.createCourier(courierData);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_CONFLICT)
                .and()
                .body("message", is("Этот логин уже используется. Попробуйте другой."));
    }

    @DisplayName("Check courier can't be created without login")
    @Test
    public void cantCreateCourierWithoutLoginTest() {

        createCourier(null, password, firstName);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));

    }

    @DisplayName("Check courier can't be created without password")
    @Test
    public void cantCreateCourierWithoutPasswordTest() {

        createCourier(login, null, firstName);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
