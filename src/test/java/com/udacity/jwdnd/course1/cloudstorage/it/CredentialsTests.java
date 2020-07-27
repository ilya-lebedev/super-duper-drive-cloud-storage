package com.udacity.jwdnd.course1.cloudstorage.it;

import com.udacity.jwdnd.course1.cloudstorage.it.helpers.LoginHelper;
import com.udacity.jwdnd.course1.cloudstorage.it.helpers.SignupHelper;
import com.udacity.jwdnd.course1.cloudstorage.it.page_objects.HomePage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.*;
import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialsTests {

    private static final String FIRST_CREDENTIAL_URL = "first-url.com";
    private static final String FIRST_CREDENTIAL_USERNAME = "first_username";
    private static final String FIRST_CREDENTIAL_PASSWORD = "first_password";
    private static final String SECOND_CREDENTIAL_URL = "second-url.com";
    private static final String SECOND_CREDENTIAL_USERNAME = "second_username";
    private static final String SECOND_CREDENTIAL_PASSWORD = "second_password";
    private static final String THIRD_CREDENTIAL_URL = "third-url.com";
    private static final String THIRD_CREDENTIAL_PASSWORD = "third_password";
    private static final String THIRD_CREDENTIAL_USERNAME = "third_username";
    private static final String EDITED_CREDENTIAL_URL = "third-url.com";

    private static WebDriver driver;

    @LocalServerPort
    private Integer port;

    private static boolean isInitialized = false;

    private HomePage homePage;

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
            LoginHelper.login(driver, port, USER_NAME, PASSWORD);

            homePage = new HomePage(driver);

            driver.get("http://localhost:" + port);
            homePage.openCredentialsTab();
            homePage.openCredentialModal();
            homePage.addNewCredential(FIRST_CREDENTIAL_URL, FIRST_CREDENTIAL_USERNAME, FIRST_CREDENTIAL_PASSWORD);

            driver.get("http://localhost:" + port);
            homePage.openCredentialsTab();
            homePage.openCredentialModal();
            homePage.addNewCredential(SECOND_CREDENTIAL_URL, SECOND_CREDENTIAL_USERNAME, SECOND_CREDENTIAL_PASSWORD);

            isInitialized = true;
        }

        driver.get("http://localhost:" + port);
        homePage = new HomePage(driver);
        homePage.openCredentialsTab();
    }

    @Test
    public void createCredential() {
        homePage.openCredentialModal();

        assertTrue(homePage.isCredentialModalOpened());

        homePage.addNewCredential(THIRD_CREDENTIAL_URL, THIRD_CREDENTIAL_USERNAME, THIRD_CREDENTIAL_PASSWORD);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openCredentialsTab();

        assertTrue(homePage.isCredentialInList(THIRD_CREDENTIAL_URL));
    }

    @Test
    public void editCredential() {
        homePage.openCredentialModalForEdit(FIRST_CREDENTIAL_URL);

        assertTrue(homePage.isCredentialModalOpened());

        homePage.changeCredential(EDITED_CREDENTIAL_URL);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openCredentialsTab();

        assertTrue(homePage.isCredentialInList(EDITED_CREDENTIAL_URL));
    }

    @Test
    public void deleteCredential() {
        homePage.deleteCredential(SECOND_CREDENTIAL_URL);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openCredentialsTab();

        assertFalse(homePage.isCredentialInList(SECOND_CREDENTIAL_URL));
    }

}
