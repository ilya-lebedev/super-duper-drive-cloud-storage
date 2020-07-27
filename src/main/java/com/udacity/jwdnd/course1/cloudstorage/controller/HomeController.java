package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    private NotesService notesService;

    public HomeController(NotesService notesService) {
        this.notesService = notesService;
    }

    @GetMapping
    public String view(Model model) {
        model.addAttribute("noteForm", new Note());
        model.addAttribute("notes", notesService.getUserNotes(1));
        return "home";
    }

}
