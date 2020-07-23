package com.udacity.jwdnd.course1.cloudstorage.it.helpers;

import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.LoginPage;
import org.openqa.selenium.WebDriver;

public class LoginHelper {

    public static void login(WebDriver driver, Integer port, String userName, String password) {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + port + "/login");
        loginPage.login(userName, password);
    }

    public LoginHelper() {}

}
