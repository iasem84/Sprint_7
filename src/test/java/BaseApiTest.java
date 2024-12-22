import api.CourierApi;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.LoginData;
import model.LoginResponseData;
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
    protected int id = 0;

    @Before
    public void courierInit() {
        courierApi = new CourierApi();
        login = RandomStringUtils.randomAlphabetic(8);
        password = RandomStringUtils.randomAlphabetic(8);
        firstName = RandomStringUtils.randomAlphabetic(8);
    }

    @After
    public void cleanUp() {
//        if (response.extract().statusCode() == HttpStatus.SC_CREATED ||
//                response.extract().statusCode() == HttpStatus.SC_CONFLICT) {
            response = courierApi.loginCourier(new LoginData(login, password));

            id = response.extract().body().jsonPath().getInt("id");
            if (id != 0){
                response = courierApi.deleteCourier(id);
            }
    }
}
