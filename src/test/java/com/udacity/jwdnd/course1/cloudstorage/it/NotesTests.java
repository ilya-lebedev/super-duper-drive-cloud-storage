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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NotesTests {

    private static final String FIRST_NOTE_TITLE = "First note title";
    private static final String FIRST_NOTE_DESC = "First note description";
    private static final String SECOND_NOTE_TITLE = "Second note title";
    private static final String SECOND_NOTE_DESC = "Second note description";
    private static final String THIRD_NOTE_TITLE = "Third note title";
    private static final String THIRD_NOTE_DESC = "Third note description";
    private static final String EDITED_NOTE_TITLE = "Edited note title";

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
            homePage.openNotesTab();
            homePage.openNoteModal();
            homePage.addNewNote(FIRST_NOTE_TITLE, FIRST_NOTE_DESC);

            driver.get("http://localhost:" + port);
            homePage.openNotesTab();
            homePage.openNoteModal();
            homePage.addNewNote(SECOND_NOTE_TITLE, SECOND_NOTE_DESC);

            isInitialized = true;
        }

        driver.get("http://localhost:" + port);
        homePage = new HomePage(driver);
        homePage.openNotesTab();
    }

    @Test
    public void createNote() {
        homePage.openNoteModal();

        assertTrue(homePage.isNoteModalOpened());

        homePage.addNewNote(THIRD_NOTE_TITLE, THIRD_NOTE_DESC);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertTrue(homePage.isNoteInList(THIRD_NOTE_TITLE));
    }

    @Test
    public void editNote() {
        homePage.openNoteModalForEdit(FIRST_NOTE_TITLE);

        assertTrue(homePage.isNoteModalOpened());

        homePage.changeNote(EDITED_NOTE_TITLE);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertTrue(homePage.isNoteInList(EDITED_NOTE_TITLE));
    }

    @Test
    public void deleteNote() {
        homePage.deleteNote(SECOND_NOTE_TITLE);

        assertEquals("http://localhost:" + port + "/result", driver.getCurrentUrl());

        driver.get("http://localhost:" + port);
        homePage.openNotesTab();

        assertFalse(homePage.isNoteInList(EDITED_NOTE_TITLE));
    }

}
