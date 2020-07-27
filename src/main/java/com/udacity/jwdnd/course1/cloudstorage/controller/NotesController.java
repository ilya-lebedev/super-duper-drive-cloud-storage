package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.ResultAttributesHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/notes")
public class NotesController {

    private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    @PostMapping
    public RedirectView postNoteForm(@ModelAttribute("noteForm") Note note, RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int result = notesService.saveNote(note, user.getUserId());
        ResultAttributesHelper.setResultAttribute(result, redirectAttributes);

        return new RedirectView("/result");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteNote(@PathVariable("id") Integer noteId, RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int result = notesService.deleteNote(noteId, user.getUserId());
        ResultAttributesHelper.setResultAttribute(result, redirectAttributes);

        return new RedirectView("/result");
    }

}
