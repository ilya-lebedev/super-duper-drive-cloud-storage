package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.HomePage;
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

import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupLoginLogoutTests {

    @LocalServerPort
    private Integer port;

    private static WebDriver driver;

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
    public void signupLoginLogout() {
        driver.get("http://localhost:" + port);
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/files/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/files/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/notes/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/notes/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/credentials/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/credentials/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/result");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        SignupPage signupPage = new SignupPage(driver);
        driver.get("http://localhost:" + port + "/signup");

        signupPage.signup(FIRST_NAME, LAST_NAME, USER_NAME, PASSWORD);

        assertEquals("http://localhost:" + port + "/signup", driver.getCurrentUrl());

        LoginPage loginPage = new LoginPage(driver);
        driver.get("http://localhost:" + port + "/login");

        loginPage.login(USER_NAME, PASSWORD);

        assertEquals("http://localhost:" + port + "/", driver.getCurrentUrl());

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/files/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/files/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/notes/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/notes/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/credentials/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/credentials/delete/1");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());

        driver.get("http://localhost:" + port + "/result");
        assertEquals("http://localhost:" + port + "/login", driver.getCurrentUrl());
    }

}
