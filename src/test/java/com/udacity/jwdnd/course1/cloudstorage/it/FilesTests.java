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

import java.io.File;

import static com.udacity.jwdnd.course1.cloudstorage.it.data.UserOne.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilesTests {

    private static final String TEST_FILE_ONE = "TestFileOne.txt";
    private static final String TEST_FILE_TWO = "TestFileTwo.txt";

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

            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource(TEST_FILE_ONE).getFile());
            String absolutePath = file.getAbsolutePath();

            homePage.uploadFile(absolutePath);

            isInitialized = true;
        }

        driver.get("http://localhost:" + port);
        homePage = new HomePage(driver);
    }

    @Test
    public void uploadFile() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(TEST_FILE_TWO).getFile());
        String absolutePath = file.getAbsolutePath();

        homePage.uploadFile(absolutePath);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);

        assertTrue(homePage.isFileInList(TEST_FILE_TWO));
    }

    @Test
    public void deleteFile() {
        homePage.deleteFile(TEST_FILE_ONE);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);

        assertFalse(homePage.isFileInList(TEST_FILE_ONE));
    }

}
