package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
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

    @Autowired
    public HomeController(FileService fileService, UserService userService, NoteService noteService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
    }

    @GetMapping()
    public String homeView() {
        return "redirect:home/files";
    }

    @GetMapping("/files")
    public String filesPage(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        Integer userId = user.getUserId();
        model.addAttribute("fileList", fileService.getFilesByUser(userId));
        return "home";
    }

    @GetMapping("/notes")
    public String notesPage(Principal principal, Model model) {
        User user = userService.getUser(principal.getName());
        Integer userId = user.getUserId();
        model.addAttribute("noteList", noteService.getNotesByUser(userId));
        return "home";
    }

    @GetMapping("/credentials")
    public String credentialsPage(Principal principal, Model model) {
//        User user = userService.getUser(principal.getName());
//        Integer userId = user.getUserId();
//        model.addAttribute("credentialsList", credentialsServide.getCredentials());
        return "home";
    }
}