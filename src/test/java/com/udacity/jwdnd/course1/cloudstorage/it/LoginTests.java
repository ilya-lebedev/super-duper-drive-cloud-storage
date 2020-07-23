package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.helpers.SignupHelper;
import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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

    private boolean isInitialized = false;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }

    @BeforeEach
    public void beforeEach() {
        if (!isInitialized) {
            SignupHelper.signUp(driver, port, FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);
        }
    }

    @Test
    public void loginSuccess() {
        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + port + "/login");
        assertNull(loginPage.getErrorAlert());
        assertNull(loginPage.getSuccessAlert());

        loginPage.login(USER_NAME, PASSWORD);

        assertEquals("http://localhost:" + port + "/", driver.getCurrentUrl());
    }

}
