package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialsPage {
    WebDriver driver;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "add-credential-btn")
    private WebElement addCredentialButton;

    @FindBy(className = "alert-danger")
    private WebElement credentialsErrorMessage;

    @FindBy(className = "alert-success")
    private WebElement credentialsSuccessMessage;

    @FindBy(css = "table#credential-table .credential-table__cred")
    private List<WebElement> credentials;

    @FindBy(id = "credentialModal")
    private WebElement credentialModal;

    @FindBy(id = "credential-url")
    private WebElement credentialURL;

    @FindBy(id = "credential-username")
    private WebElement credentialUserName;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "credential-submit")
    private WebElement credentialSubmitBtn;

    public CredentialsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openCredentialsTab() {
        this.credentialsTab.click();
    }

    public void openCredentialsModal() {
        this.addCredentialButton.click();
    }

    public void openCredentialsModal(Integer index) {
        this.credentials.get(index).findElement(By.className("credential-table__cred-edit")).click();
    }

    public void submitCredentialsModal(String url, String username, String password) {
        new WebDriverWait(driver, 1000).until(ExpectedConditions.elementToBeClickable(By.id("credential-url")));
        this.credentialURL.clear();
        this.credentialURL.sendKeys(url);
        this.credentialUserName.clear();
        this.credentialUserName.sendKeys(username);
        this.credentialPassword.clear();
        this.credentialPassword.sendKeys(password);
        this.credentialSubmitBtn.click();
    }

    public Boolean hasCredentialsSuccessMessage() {
        try {
            credentialsSuccessMessage.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean hasCredentialsErrorMessage() {
        try {
            credentialsErrorMessage.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean hasCredentials() {
        return this.credentials.size() > 0;
    }

    public int getNumberOfCredentials() {
        return this.credentials.size();
    }

    public Boolean hasCredential(String url, String userName) {
        for (WebElement cred : credentials) {
            String credURL = cred.findElement(By.className("credential-table__cred-url")).getText();
            String credUserName = cred.findElement(By.className("credential-table__cred-user-name")).getText();
            if (url.equals(credURL) && userName.equals(credUserName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteCredential(int index) {
        this.credentials.get(index).findElement(By.className("credential-table__cred-delete")).click();
    }


}
