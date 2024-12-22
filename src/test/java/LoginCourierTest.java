import io.qameta.allure.junit4.DisplayName;
import model.CourierData;
import model.LoginData;
import model.LoginResponseData;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;

public class LoginCourierTest extends BaseApiTest{

    @DisplayName("Check courier can log in")
    @Test
    public void courierCanLogInTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        response = courierApi.createCourier(courierData);
        loginData = new LoginData(login, password);
        response = courierApi.loginCourier(loginData);

        id = response.extract().body().jsonPath().getInt("id");

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(id));
    }

    @DisplayName("Check courier can't log in with non-existent user")
    @Test
    public void courierCantLogInWithNonExistentUserTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        loginData = new LoginData(login, password);
        response = courierApi.loginCourier(loginData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Check courier can't log in without login")
    @Test
    public void courierCantLogInWithoutLoginTest() {

        courierData = new CourierData(login, password);
        //вызываем метод
        response = courierApi.createCourier(courierData);
        loginData = new LoginData(null, password);
        response = courierApi.loginCourier(loginData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can't log in without password")
    @Test
    public void courierCantLogInWithoutPasswordTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        response = courierApi.createCourier(courierData);
        loginData = new LoginData(login, null);
        response = courierApi.loginCourier(loginData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @DisplayName("Check courier can't log in with wrong login")
    @Test
    public void courierCantLogInWithWrongLoginTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        response = courierApi.createCourier(courierData);
        loginData = new LoginData("a" + login, password);
        response = courierApi.loginCourier(loginData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @DisplayName("Check courier can't log in with wrong password")
    @Test
    public void courierCantLogInWithWrongPasswordTest() {

        courierData = new CourierData(login, password, firstName);
        //вызываем метод
        response = courierApi.createCourier(courierData);
        loginData = new LoginData(login, "a" + password);
        response = courierApi.loginCourier(loginData);

        //проверка
        response.log().all()
                .assertThat()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

}
