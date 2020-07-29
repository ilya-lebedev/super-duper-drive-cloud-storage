package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.exeption.FileNameAlreadyExists;
import com.udacity.jwdnd.course1.cloudstorage.helper.ResultAttributesHelper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FilesService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;

@Controller
@RequestMapping("/files")
public class FileController {

    private FilesService filesService;

    public FileController(FilesService filesService) {
        this.filesService = filesService;
    }

    @PostMapping
    public RedirectView uploadFile(@RequestParam("fileUpload") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();

        try {
            int result = filesService.uploadFile(file, user.getUserId());
            ResultAttributesHelper.setResultAttribute(result, redirectAttributes);
        } catch (FileNameAlreadyExists fileNameAlreadyExists) {
            redirectAttributes.addFlashAttribute("customError", true);
            redirectAttributes.addFlashAttribute("message",
                    "File with name '" + file.getOriginalFilename() + "' already exists");
        }

        return new RedirectView("/result");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") Integer fileId) throws FileNotFoundException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        File file = filesService.downloadFile(fileId, user.getUserId());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }

    @GetMapping("/delete/{id}")
    public RedirectView deleteFile(@PathVariable("id") Integer fileId, RedirectAttributes redirectAttributes) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int result = filesService.deleteFile(fileId, user.getUserId());
        ResultAttributesHelper.setResultAttribute(result, redirectAttributes);

        return new RedirectView("/result");
    }

    @ExceptionHandler(FileNotFoundException.class)
    public RedirectView handleFileNotFound(FileNotFoundException exception, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("customError", true);
        redirectAttributes.addFlashAttribute("message", "File not found");

        return new RedirectView("/result");
    }

}
