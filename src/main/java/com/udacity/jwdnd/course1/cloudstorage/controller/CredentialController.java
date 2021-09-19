package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialsService credentialsService;
    private final UserService userService;

    @Autowired
    public CredentialController(CredentialsService credentialsService, UserService userService) {
        this.credentialsService = credentialsService;
        this.userService = userService;
    }

    @GetMapping("/delete/{id}")
    public String deleteCredentialById(@PathVariable Integer id) {
        credentialsService.deleteCredentialById(id);
        return "redirect:/home/credentials";
    }

    @PostMapping()
    public String saveCredential(@ModelAttribute Credential credential, Principal principal, RedirectAttributes redirectAttributes) {
        try {
            if (credential.getCredentialId() == null) {
                User user = userService.getUser(principal.getName());
                credential.setUserId(user.getUserId());
                credentialsService.saveCredential(credential);
                redirectAttributes.addFlashAttribute("uploadSuccess", "Credential successfully saved");
            } else {
                credentialsService.updateCredential(credential);
                redirectAttributes.addFlashAttribute("uploadSuccess", "Credential successfully updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("uploadError", "Something went wrong, try again");
        }
        return "redirect:/home/credentials";
    }


}
