package com.udacity.jwdnd.course1.cloudstorage.it.helpers;

import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.SignupPage;
import org.openqa.selenium.WebDriver;

public class SignupHelper {

    public static void signUp(WebDriver driver, Integer port,
                              String firstName, String lastName, String userName, String password) {
        SignupPage signupPage = new SignupPage(driver);
        driver.get("http://localhost:" + port + "/signup");
        signupPage.signup(firstName, lastName, userName, password);
    }

    private SignupHelper() {}

}
