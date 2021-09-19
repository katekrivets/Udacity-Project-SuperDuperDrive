package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public String deleteNoteById(@PathVariable Integer id) {
        noteService.deleteNoteById(id);
        return "redirect:/home/notes";
    }

    @PostMapping()
    public String saveNote(@ModelAttribute Note note, Principal principal) {
        if (note.getNoteId() == null) {
            User user = userService.getUser(principal.getName());
            note.setUserId(user.getUserId());
            noteService.saveNote(note);
        } else {
            noteService.updateNote(note);
        }
        return "redirect:/home/notes";
    }
}
