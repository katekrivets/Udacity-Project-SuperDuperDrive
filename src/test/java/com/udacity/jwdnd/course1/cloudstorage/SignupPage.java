package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    @FindBy(id = "inputLastName")
    private WebElement lastName;

    @FindBy(id = "inputUsername")
    private WebElement userName;

    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "submitBtn")
    private WebElement submitBtn;

    @FindBy(id = "signupSuccess")
    private WebElement signupSuccess;

    @FindBy(id = "signupError")
    private WebElement signupError;


    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public Boolean hasSuccessMessage() {
        try {
            signupSuccess.isDisplayed();
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    public Boolean hasErrorMessage() {
        try {
            signupError.isDisplayed();
            return true;
        } catch(NoSuchElementException e) {
            return false;
        }
    }

    public void signup(String firstName, String lastName, String userName, String password) {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.userName.sendKeys(userName);
        this.password.sendKeys(password);
        this.submitBtn.click();
    }

    public void signup(String firstName, String lastName, String userName) {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.userName.sendKeys(userName);
        this.password.sendKeys("");
        this.submitBtn.click();
    }

    public void signup(String firstName, String lastName) {
        this.firstName.sendKeys(firstName);
        this.lastName.sendKeys(lastName);
        this.userName.sendKeys("");
        this.password.sendKeys("");
        this.submitBtn.click();
    }


}
