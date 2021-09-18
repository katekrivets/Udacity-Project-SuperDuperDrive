package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;

@Controller
@RequestMapping("/files")
public class FilesController {
    private final FileService fileService;
    private final UserService userService;

    @Autowired
    public FilesController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping()
    public String getAllFiles() {
        return "redirect:/home";
    }

    @GetMapping("/{id}")
    public void getFileById(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(id);
        response.setContentType(file.getContentType());
        InputStream is = new ByteArrayInputStream(file.getFileData());
        IOUtils.copy(is, response.getOutputStream());
    }

//    check for uniqueness
//    check if null
//    check for error
//    check size
    @PostMapping()
    public String uploadFile(@RequestParam("fileUpload") MultipartFile multipartFile, Principal principal) {
        User user = userService.getUser(principal.getName());
        File file;
        try {
            file = new File(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()),
                    multipartFile.getBytes(),
                    user.getUserId()
            );
            fileService.uploadFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteFileById(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        fileService.deleteFileById(id);
        return "redirect:/home";
    }


}
