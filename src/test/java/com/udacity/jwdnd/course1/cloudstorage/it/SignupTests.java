package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.SignupPage;
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
public class SignupTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

    private SignupPage signupPage;

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
        signupPage = new SignupPage(driver);
        driver.get("http://localhost:" + port + "/signup");
    }

    @Test
    public void signupSuccess() {

        signupPage.signup(FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);

        assertEquals("http://localhost:" + port + "/signup", driver.getCurrentUrl());
        assertTrue(signupPage.getSuccessAlert().contains("You successfully signed up!"));
        assertNull(signupPage.getErrorAlert());
    }

}
