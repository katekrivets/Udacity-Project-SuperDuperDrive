package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/home")
public class HomeController {
    private final UserService userService;
    private final FileService fileService;
    private final NoteService noteService;
    private final CredentialsService credentialsService;

    @Autowired
    public HomeController(
            FileService fileService,
            UserService userService,
            NoteService noteService,
            CredentialsService credentialsService
    ) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialsService = credentialsService;
    }

    @GetMapping()
    public String homeView() {
        return "redirect:home/files";
    }

    @GetMapping("/files")
    public String filesPage(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("fileList", fileService.getFilesByUser(user.getUserId()));
        return "home";
    }

    @GetMapping("/notes")
    public String notesPage(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("noteList", noteService.getNotesByUser(user.getUserId()));
        return "home";
    }

    @GetMapping("/credentials")
    public String credentialsPage(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        model.addAttribute("credentialsList", credentialsService.getCredentials(user.getUserId()));
        return "home";
    }
}