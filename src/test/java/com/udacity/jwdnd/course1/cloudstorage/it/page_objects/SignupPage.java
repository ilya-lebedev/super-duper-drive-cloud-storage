package com.udacity.jwdnd.course1.cloudstorage.it.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {

    private WebDriver driver;

    @FindBy(id = "alertError")
    private WebElement alertError;

    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUsername")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(id = "buttonSubmit")
    private WebElement buttonSubmit;

    public SignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void signup(String firstName, String lastName, String userName, String password) {
        inputFirstName.sendKeys(firstName);
        inputLastName.sendKeys(lastName);
        inputUserName.sendKeys(userName);
        inputPassword.sendKeys(password);
        buttonSubmit.submit();
    }

    public String getErrorAlert() {
        if (driver.findElements(By.id("alertError")).size() > 0) {
            return alertError.getText();
        } else {
            return null;
        }
    }

}
