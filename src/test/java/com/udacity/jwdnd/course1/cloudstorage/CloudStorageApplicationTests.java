package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.constants.Constants;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    @Order(2)
    public void signupPageSuccess() {
        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());

        SignupPage signupPage = new SignupPage(driver);
        // positive result
        signupPage.signup(
                Constants.USER_FIRST_NAME,
                Constants.USER_LAST_NAME,
                Constants.USER_NAME,
                Constants.USER_PASSWORD
        );

        Assertions.assertTrue(signupPage.hasSuccessMessage());
        Assertions.assertFalse(signupPage.hasErrorMessage());
    }

    @Test
    @Order(3)
    public void signupPageFail() {
        driver.get("http://localhost:" + this.port + "/signup");

        SignupPage signupPage = new SignupPage(driver);
        // negative result: there can't be more than one user with same userName

        signupPage.signup(
                Constants.USER_FIRST_NAME,
                Constants.USER_LAST_NAME,
                Constants.USER_NAME,
                Constants.USER_PASSWORD
        );

        Assertions.assertFalse(signupPage.hasSuccessMessage());
        Assertions.assertTrue(signupPage.hasErrorMessage());
    }

    @Test
    @Order(4)
    public void loginPage() {
        driver.get("http://localhost:" + this.port + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constants.USER_NAME, Constants.USER_PASSWORD);

        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/files");
    }

    @Order(5)
    @Test
    public void loginPageFail() {
        driver.get("http://localhost:" + this.port + "/login");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Constants.USER_NAME_ERROR, Constants.USER_PASSWORD_ERROR);

        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/login?error");
        Assertions.assertTrue(loginPage.hasErrorMessage());
    }

    @Test
    @Order(6)
    public void createNote() {
        loginPage();
        NotesPage notesPage = new NotesPage(driver);
        // locate to notes tab
        notesPage.openNotesTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/notes");
        // create two notes
        notesPage.openNoteModal();
        notesPage.submitNoteModal(Constants.NOTE_TITLE, Constants.NOTE_DESCRIPTION);
        notesPage.openNoteModal();
        notesPage.submitNoteModal(Constants.NOTE_TITLE_1, Constants.NOTE_DESCRIPTION_1);
        // check if notes were created successfully
        Assertions.assertTrue(notesPage.hasNotesSuccessMessage());
        Assertions.assertFalse(notesPage.hasNotesErrorMessage());
        // check if notes were created
        Assertions.assertTrue(notesPage.hasNotes());
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE, Constants.NOTE_DESCRIPTION));
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE_1, Constants.NOTE_DESCRIPTION_1));
        // check number of notes
        Assertions.assertEquals(notesPage.getNumberOfNotes(), 2);
    }

    @Test
    @Order(7)
    public void editNote() {
        loginPage();
        NotesPage notesPage = new NotesPage(driver);
        // locate to notes tab
        notesPage.openNotesTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/notes");
        // check if there are notes
        Assertions.assertTrue(notesPage.hasNotes());
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE, Constants.NOTE_DESCRIPTION));
        // open note edit modal and submit changes
        notesPage.openNoteModal(0);
        notesPage.submitNoteModal(Constants.NOTE_TITLE_EDIT, Constants.NOTE_DESCRIPTION_EDIT);
        // check if note was edited successfully
        Assertions.assertTrue(notesPage.hasNotesSuccessMessage());
        Assertions.assertFalse(notesPage.hasNotesErrorMessage());
        // check for changes, check that there is still two notes in the list
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE_EDIT, Constants.NOTE_DESCRIPTION_EDIT));
        Assertions.assertEquals(2, notesPage.getNumberOfNotes());
    }

    @Test
    @Order(8)
    public void deleteNote() {
        loginPage();
        NotesPage notesPage = new NotesPage(driver);
        // locate to notes tab
        notesPage.openNotesTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/notes");
        // check if there are any notes
        Assertions.assertTrue(notesPage.hasNotes());
        Assertions.assertEquals(2, notesPage.getNumberOfNotes());
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE_EDIT, Constants.NOTE_DESCRIPTION_EDIT));
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE_1, Constants.NOTE_DESCRIPTION_1));
        // delete second note
        notesPage.deleteSelectedNote(1);
        Assertions.assertEquals(notesPage.getNumberOfNotes(), 1);
        Assertions.assertTrue(notesPage.hasNote(Constants.NOTE_TITLE_EDIT, Constants.NOTE_DESCRIPTION_EDIT));
        // delete first note
        notesPage.deleteSelectedNote(0);
        Assertions.assertEquals(0, notesPage.getNumberOfNotes());
    }


    @Test
    @Order(9)
    public void createCredential() {
        loginPage();
        CredentialsPage credentialsPage = new CredentialsPage(driver);
        // locate to credentials tab
        credentialsPage.openCredentialsTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/credentials");
        // create two credentials
        credentialsPage.openCredentialsModal();
        credentialsPage.submitCredentialsModal(Constants.CREDENTIAL_URL, Constants.CREDENTIAL_USER_NAME, Constants.CREDENTIAL_PASSWORD);
        credentialsPage.openCredentialsModal();
        credentialsPage.submitCredentialsModal(Constants.CREDENTIAL_URL_1, Constants.CREDENTIAL_USER_NAME_1, Constants.CREDENTIAL_PASSWORD_1);
        // check if credentials were created successfully
        Assertions.assertTrue(credentialsPage.hasCredentialsSuccessMessage());
        Assertions.assertFalse(credentialsPage.hasCredentialsErrorMessage());
        // check if credentials were created
        Assertions.assertTrue(credentialsPage.hasCredentials());
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL, Constants.CREDENTIAL_USER_NAME));
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL_1, Constants.CREDENTIAL_USER_NAME_1));
        // check number of credentials
        Assertions.assertEquals(credentialsPage.getNumberOfCredentials(), 2);
    }

    @Test
    @Order(10)
    public void editCredential() {
        loginPage();
        CredentialsPage credentialsPage = new CredentialsPage(driver);
        // locate to credentials tab
        credentialsPage.openCredentialsTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/credentials");
        // check if there are credentials
        Assertions.assertTrue(credentialsPage.hasCredentials());
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL, Constants.CREDENTIAL_USER_NAME));
        // open credential edit modal and submit changes
        credentialsPage.openCredentialsModal(0);
        credentialsPage.submitCredentialsModal(Constants.CREDENTIAL_URL_EDIT, Constants.CREDENTIAL_USER_NAME_EDIT, Constants.CREDENTIAL_PASSWORD_EDIT);
        // check if credential was edited successfully
        Assertions.assertTrue(credentialsPage.hasCredentialsSuccessMessage());
        Assertions.assertFalse(credentialsPage.hasCredentialsErrorMessage());
        // check for changes, check that there is still two credentials in the list
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL_EDIT, Constants.CREDENTIAL_USER_NAME_EDIT));
        Assertions.assertEquals(2, credentialsPage.getNumberOfCredentials());
    }

    @Test
    @Order(11)
    public void deleteCredential() {
        loginPage();
        CredentialsPage credentialsPage = new CredentialsPage(driver);
        // locate to credentials tab
        credentialsPage.openCredentialsTab();
        Assertions.assertEquals(driver.getCurrentUrl(), "http://localhost:" + this.port + "/home/credentials");
        // check if there are any credentials
        Assertions.assertTrue(credentialsPage.hasCredentials());
        Assertions.assertEquals(2, credentialsPage.getNumberOfCredentials());
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL_EDIT, Constants.CREDENTIAL_USER_NAME_EDIT));
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL_1, Constants.CREDENTIAL_USER_NAME_1));
        // delete second credential
        credentialsPage.deleteCredential(1);
        Assertions.assertEquals(1, credentialsPage.getNumberOfCredentials());
        Assertions.assertTrue(credentialsPage.hasCredential(Constants.CREDENTIAL_URL_EDIT, Constants.CREDENTIAL_USER_NAME_EDIT));
        // delete first credential
        credentialsPage.deleteCredential(0);
        Assertions.assertEquals(0, credentialsPage.getNumberOfCredentials());
    }

}
