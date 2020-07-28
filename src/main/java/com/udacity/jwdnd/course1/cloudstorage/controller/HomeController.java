package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.service.NotesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    private NotesService notesService;
    private CredentialsService credentialsService;

    public HomeController(NotesService notesService, CredentialsService credentialsService) {
        this.notesService = notesService;
        this.credentialsService = credentialsService;
    }

    @GetMapping
    public String view(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        model.addAttribute("noteForm", new Note());
        model.addAttribute("notes", notesService.getUserNotes(user.getUserId()));
        model.addAttribute("credentialForm", new CredentialForm());
        model.addAttribute("credentials", credentialsService.getUserCredentials(user.getUserId()));
        return "home";
    }

}
