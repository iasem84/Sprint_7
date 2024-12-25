import io.qameta.allure.junit4.DisplayName;
import model.CourierData;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;

public class LoginCourierTest extends BaseApiTest{

    @DisplayName("Check courier can log in")
    @Test
    public void courierCanLogInTest() {

        createCourier(login, password, firstName);
        loginCourier(login, password);
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .and()
                .body(containsString("id"));
    }

    @DisplayName("Check courier can't log in with non-existent user")
    @Test
    public void courierCantLogInWithNonExistentUserTest() {

        courierData = new CourierData(login, password, firstName);
        loginCourier(login, password);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Check courier can't log in without login")
    @Test
    public void courierCantLogInWithoutLoginTest() {

        createCourier(login, password, firstName);
        loginCourier(null, password);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can't log in without password")
    @Test
    public void courierCantLogInWithoutPasswordTest() {

        createCourier(login, password, firstName);
        loginCourier(login, null);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .and()
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can't log in with wrong login")
    @Test
    public void courierCantLogInWithWrongLoginTest() {

        createCourier(login, password, firstName);
        loginCourier("a" + login, password);

        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .and()
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Check courier can't log in with wrong password")
    @Test
    public void courierCantLogInWithWrongPasswordTest() {

        createCourier(login, password, firstName);
        loginCourier(login, "a" + password);

        response.log().all()
                .assertThat()
                .and()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }
}
