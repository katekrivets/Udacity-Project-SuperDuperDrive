package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;
    private final UserService userService;

    @Autowired
    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping("/delete/{id}")
    public String deleteNoteById(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            noteService.deleteNoteById(id);
            redirectAttributes.addFlashAttribute("uploadSuccess", "Note successfully deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("uploadError", "Something went wrong during deletion");
            e.printStackTrace();
        }
        return "redirect:/home/notes";
    }

    @PostMapping()
    public String saveNote(@ModelAttribute Note note, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            if (note.getNoteId() == null) {
                User user = userService.getUser(principal.getName());
                note.setUserId(user.getUserId());
                noteService.saveNote(note);
                redirectAttributes.addFlashAttribute("uploadSuccess", "Note successfully created");
            } else {
                noteService.updateNote(note);
                redirectAttributes.addFlashAttribute("uploadSuccess", "Note successfully updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("uploadError", "Something went wrong, try again");
        }
        return "redirect:/home/notes";
    }
}
