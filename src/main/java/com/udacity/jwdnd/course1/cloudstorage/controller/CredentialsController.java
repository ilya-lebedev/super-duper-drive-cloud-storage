package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.helpers.ResultAttributesHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/credentials")
public class CredentialsController {

    private CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping
    public RedirectView postCredentialForm(@ModelAttribute("credentialForm") CredentialForm credentialForm,
                                           RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int result = credentialsService.saveCredential(credentialForm, user.getUserId());
        ResultAttributesHelper.setResultAttribute(result, redirectAttributes);

        return new RedirectView("/result");
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteNote(@PathVariable("id") Integer credentialId, RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int result = credentialsService.deleteCredential(credentialId, user.getUserId());
        ResultAttributesHelper.setResultAttribute(result, redirectAttributes);

        return new RedirectView("/result");
    }

}
