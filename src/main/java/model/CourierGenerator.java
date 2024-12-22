package model;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class CourierGenerator {
    @Step("Generate random courier")
    public static CourierData getRandomCourier(){
        String login = RandomStringUtils.randomAlphabetic(8);
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier without login")
    public static CourierData getRandomCourierWithoutLogin(){
        String login = null;
        String password = RandomStringUtils.randomAlphabetic(8);
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierData(login, password, firstName);
    }

    @Step("Generate random courier without password")
    public static CourierData getRandomCourierWithoutPassword(){
        String login = RandomStringUtils.randomAlphabetic(8);;
        String password = null;
        String firstName = RandomStringUtils.randomAlphabetic(8);

        return new CourierData(login, password, firstName);
    }
}
