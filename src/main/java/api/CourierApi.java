package api;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.CourierData;
import model.LoginData;

import static io.restassured.RestAssured.given;

public class CourierApi extends RestApi{
    public static final String CREATE_COURIER_URI = "/api/v1/courier";
    public static final String LOGIN_COURIER_URI = "/api/v1/courier/login";
    public static final String DELETE_COURIER_URI = "/api/v1/courier/%d";

    @Step("Create courier")
    public ValidatableResponse createCourier(CourierData courier){
        return given()
                .spec(requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(CREATE_COURIER_URI)
                .then();
    }

    @Step("Login courier")
    public ValidatableResponse loginCourier(LoginData login){
        return given()
                .spec(requestSpecification())
                .and()
                .body(login)
                .when()
                .post(LOGIN_COURIER_URI)
                .then();
    }

    @Step("Delete courier")
    public ValidatableResponse deleteCourier(int id){
        return given()
                .spec(requestSpecification())
                .and()
                .delete(String.format(DELETE_COURIER_URI, id))
                .then();
    }
}
