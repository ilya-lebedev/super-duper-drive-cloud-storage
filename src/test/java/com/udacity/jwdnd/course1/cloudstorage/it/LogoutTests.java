package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.helpers.LoginHelper;
import com.udacity.jwdnd.course1.cloudstorage.it.helpers.SignupHelper;
import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LogoutTests {

    private static WebDriver driver;

    @LocalServerPort
    private Integer port;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @Test
    public void logout() {
        SignupHelper.signUp(driver, port, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);
        LoginHelper.login(driver, port, USER_NAME, PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());
    }

}
