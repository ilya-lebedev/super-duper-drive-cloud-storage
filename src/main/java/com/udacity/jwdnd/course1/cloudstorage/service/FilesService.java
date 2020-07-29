package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.exeption.FileNameAlreadyExists;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@Service
public class FilesService {

    private FilesMapper filesMapper;

    public FilesService(FilesMapper filesMapper) {
        this.filesMapper = filesMapper;
    }

    public List<File> getUserFiles(Integer userId) {
        return filesMapper.getUserFiles(userId);
    }

    public int uploadFile(MultipartFile multipartFile, Integer userId) throws FileNameAlreadyExists {
        File existedFile = filesMapper.getFileByNameAndUserId(multipartFile.getOriginalFilename(), userId);
        if (existedFile != null) {
            throw new FileNameAlreadyExists();
        }

        File file = new File();

        try {
            file.setFileData(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }

        if (file.getFileData() == null || file.getFileData().length == 0
                || multipartFile.getOriginalFilename() == null || multipartFile.getOriginalFilename().isBlank()) {
            return -1;
        }

        file.setFileName(multipartFile.getOriginalFilename());
        file.setContentType(multipartFile.getContentType());
        file.setFileSize(Long.toString(multipartFile.getSize()));
        file.setUserId(userId);

        return filesMapper.insert(file);
    }

    public File downloadFile(Integer fileId, Integer userId) throws FileNotFoundException {
        File file = filesMapper.getFile(fileId);
        if (file != null && userId.equals(file.getUserId())) {
            return file;
        } else {
            throw new FileNotFoundException();
        }
    }

    public int deleteFile(Integer fileId, Integer userId) {
        File file = filesMapper.getFile(fileId);
        if (file != null && userId.equals(file.getUserId())) {
            return filesMapper.delete(fileId);
        } else {
            return -1;
        }
    }

}
