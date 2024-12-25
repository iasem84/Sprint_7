import api.CourierApi;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.LoginData;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;

public class BaseApiTest {
    protected CourierData courierData;
    protected CourierApi courierApi;
    protected LoginData loginData;
    protected ValidatableResponse response;

    protected String login;
    protected String password;
    protected String firstName;

    protected int courierId;
    protected int responseCode;

    @Before
    public void courierInit() {
        courierApi = new CourierApi();
        login = RandomStringUtils.randomAlphabetic(8);
        password = RandomStringUtils.randomAlphabetic(8);
        firstName = RandomStringUtils.randomAlphabetic(8);
    }

    @After
    public void cleanUp() {
        if (responseCode == HttpStatus.SC_CREATED) {
            loginCourier(login, password);
            courierId = response.extract().body().jsonPath().getInt("id");
            response = courierApi.deleteCourier(courierId);
            response.log().all()
                    .assertThat()
                    .statusCode(HttpStatus.SC_OK);
        }
    }

    protected void createCourier(String login, String password, String firstName) {
        courierData = new CourierData(login, password, firstName);
        response = courierApi.createCourier(courierData);
        responseCode = response.extract().statusCode();
    }

    protected void loginCourier(String login, String password) {
        loginData = new LoginData(login, password);
        response = courierApi.loginCourier(loginData);
    }
}
