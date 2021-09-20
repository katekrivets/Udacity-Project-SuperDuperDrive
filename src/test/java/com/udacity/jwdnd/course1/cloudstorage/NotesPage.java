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

public class NotesPage {
    WebDriver driver;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;
    /**
     * NOTES
     */
    @FindBy(id = "add-note-btn")
    private WebElement addNoteButton;

    @FindBy(id = "notes-error")
    private WebElement notesErrorMessage;

    @FindBy(id = "notes-success")
    private WebElement notesSuccessMessage;

    @FindBy(css = "table#notes-table .notes-table__note")
    private List<WebElement> notes;

    @FindBy(id = "noteModal")
    private WebElement noteModal;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-submit-button")
    private WebElement noteSubmitBtn;

    /**
     * END NOTES
     */


    public NotesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void openNotesTab() {
        this.notesTab.click();
    }

    public void openNoteModal() {
        this.addNoteButton.click();
    }

    public void openNoteModal(Integer index) {
        this.notes.get(index).findElement(By.className("notes-table__note-edit")).click();
    }

    public void submitNoteModal(String noteTitle, String noteDescription) {
        new WebDriverWait(driver, 1000).until(ExpectedConditions.elementToBeClickable(By.id("note-title")));
        this.noteTitle.clear();
        this.noteTitle.sendKeys(noteTitle);
        this.noteDescription.clear();
        this.noteDescription.sendKeys(noteDescription);
        this.noteSubmitBtn.click();
    }

    public Boolean hasNotesSuccessMessage() {
        try {
            notesSuccessMessage.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean hasNotesErrorMessage() {
        try {
            notesErrorMessage.isDisplayed();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public Boolean hasNotes() {
        return this.notes.size() > 0;
    }

    public int getNumberOfNotes() {
        return this.notes.size();
    }

    public Boolean hasNote(String title, String description) {
        for (WebElement note : notes) {
            String noteTitle = note.findElement(By.className("notes-table__note-title")).getText();
            String noteDescription = note.findElement(By.className("notes-table__note-description")).getText();
            if (noteTitle.equals(title) && description.equals(noteDescription)) {
                return true;
            }
        }
        return false;
    }

    public void deleteSelectedNote(int index) {
        this.notes.get(index).findElement(By.className("notes-table__note-delete")).click();
    }

}
