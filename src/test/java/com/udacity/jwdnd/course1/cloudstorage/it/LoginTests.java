package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.LoginPage;
import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.SignupPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.*;

import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginTests {

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
    public void loginSuccess() {
        SignupPage signupPage = new SignupPage(driver);
        driver.get("http://localhost:" + port + "/signup");
        signupPage.signup(FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);

        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + port + "/login");
        assertNull(loginPage.getErrorAlert());
        assertNull(loginPage.getSuccessAlert());

        loginPage.login(USER_NAME, PASSWORD);

        assertEquals("http://localhost:" + port + "/", driver.getCurrentUrl());
    }

}
