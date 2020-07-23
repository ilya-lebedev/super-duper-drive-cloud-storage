package com.udacity.jwdnd.course1.cloudstorage.it.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    private WebDriver driver;


    @FindBy(id="btn-logout")
    private WebElement btnLogout;


    // Tabs
    @FindBy(id="nav-files-tab")
    private WebElement navFilesTab;

    @FindBy(id="nav-notes-tab")
    private WebElement navNotesTab;

    @FindBy(id="nav-credentials-tab")
    private WebElement navCredentialsTab;


    // Files Tab
    @FindBy(id="inputFileUpload")
    private WebElement inputFileUpload;

    @FindBy(id="btn-file-upload")
    private WebElement btnFileUpload;

    @FindBy(id="fileTable")
    private WebElement fileTable;


    // Notes Tab
    @FindBy(id="btn-add-new-note")
    private WebElement btnAddNewNote;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id="input-note-title")
    private WebElement inputNoteTitle;

    @FindBy(id="input-note-description")
    private WebElement inputNoteDescription;

    @FindBy(id="btn-save-note-changes")
    private WebElement btnSaveNoteChanges;

    @FindBy(id="noteTable")
    private WebElement noteTable;


    // Credentials Tab
    @FindBy(id="btn-add-new-credential")
    private WebElement btnAddNewCredential;

    @FindBy(id="credentialModal")
    private WebElement credentialModal;

    @FindBy(id="input-credential-url")
    private WebElement inputCredentialUrl;

    @FindBy(id="input-credential-username")
    private WebElement inputCredentialUsername;

    @FindBy(id="input-credential-password")
    private WebElement inputCredentialPassword;

    @FindBy(id="btn-save-credential-changes")
    private WebElement btnSaveCredentialChanges;

    @FindBy(id="credentialTable")
    private WebElement credentialTable;


    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    public void logout() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", btnLogout);
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.urlContains("login"));
    }


    /* Tabs */

    public void openFilesTab() {
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOf(navFilesTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navFilesTab);
        wait.until(ExpectedConditions.visibilityOf(fileTable));
    }

    public void openNotesTab() {
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOf(navNotesTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navNotesTab);
        wait.until(ExpectedConditions.visibilityOf(noteTable));
    }

    public void openCredentialsTab() {
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOf(navCredentialsTab));
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", navCredentialsTab);
        wait.until(ExpectedConditions.visibilityOf(credentialTable));
    }

    public boolean isFilesTabOpened() {
        return isTabOpened(navFilesTab);
    }

    public boolean isNotesTabOpened() {
        return isTabOpened(navNotesTab);
    }

    public boolean isCredentialsTabOpened() {
        return isTabOpened(navCredentialsTab);
    }


    /* Files Tab */

    public void uploadFile(String path) {
        inputFileUpload.sendKeys(path);
        btnFileUpload.click();
    }

    public void deleteFile(String filename) {
        deleteItem(fileTable, filename);
    }

    public boolean isFileInList(String filename) {
        return fileTable.getText().contains(filename);
    }


    /* Notes Tab */

    public void openNoteModal() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", btnAddNewNote);
        WebDriverWait wait = new WebDriverWait(driver,3);
        wait.until(ExpectedConditions.visibilityOf(noteModal));
    }

    public boolean isNoteModalOpened() {
        return noteModal.isDisplayed();
    }

    public void addNewNote(String title, String description) {
        inputNoteTitle.sendKeys(title);
        inputNoteDescription.sendKeys(description);
        btnSaveNoteChanges.click();
    }

    public void openNoteModalForEdit(String noteTitle) {
        openItemForEdit(noteTable, noteTitle);
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(ExpectedConditions.visibilityOf(noteModal));
    }

    public void changeNote(String noteTitle) {
        inputNoteTitle.clear();
        inputNoteTitle.sendKeys(noteTitle);
        btnSaveNoteChanges.click();
    }

    public void deleteNote(String noteTitle) {
        deleteItem(noteTable, noteTitle);
    }

    public boolean isNoteInList(String noteTitle) {
        return noteTable.getText().contains(noteTitle);
    }


    /* Credentials Tab */

    public void openCredentialModal() {
        ((JavascriptExecutor)driver).executeScript("arguments[0].click();", btnAddNewCredential);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(credentialModal));
    }

    public boolean isCredentialModalOpened() {
        return credentialModal.isDisplayed();
    }

    public void addNewCredential(String url, String username, String password) {
        inputCredentialUrl.sendKeys(url);
        inputCredentialUsername.sendKeys(username);
        inputCredentialPassword.sendKeys(password);
        btnSaveCredentialChanges.click();
    }

    public void openCredentialModalForEdit(String credentialUrl) {
        openItemForEdit(credentialTable, credentialUrl);
        WebDriverWait wait = new WebDriverWait(driver, 3);
        wait.until(ExpectedConditions.visibilityOf(credentialModal));
    }

    public void changeCredential(String url) {
        inputCredentialUrl.clear();
        inputCredentialUrl.sendKeys(url);
        btnSaveCredentialChanges.click();
    }

    public void deleteCredential(String credentialUrl) {
        deleteItem(credentialTable, credentialUrl);
    }

    public boolean isCredentialInList(String credentialUrl) {
        return credentialTable.getText().contains(credentialUrl);
    }


    /* Helpers Methods */

    private boolean isTabOpened(WebElement navTab) {
        String clazz = navTab.getAttribute("class");
        return clazz.contains("active");
    }

    private void deleteItem(WebElement table, String itemName) {
        interactWithItem(table, itemName, "btn-danger");
    }

    private void openItemForEdit(WebElement table, String itemName) {
        interactWithItem(table, itemName, "btn-success");
    }

    private void interactWithItem(WebElement table, String itemName, String buttonClass) {
        WebElement tbody = table.findElement(By.tagName("tbody"));
        List<WebElement> trs = tbody.findElements(By.tagName("tr"));
        for (WebElement tr : trs) {
            if (tr.getText().contains(itemName)) {
                WebElement button = tr.findElement(By.className(buttonClass));
                ((JavascriptExecutor)driver).executeScript("arguments[0].click();", button);
            }
        }
    }

}
