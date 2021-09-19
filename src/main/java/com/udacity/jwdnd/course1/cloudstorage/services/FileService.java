package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;

import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public List<File> getFilesByUser(Integer userId) {
        return fileMapper.getFiles(userId);
    }

    public void uploadFile(File file) throws FileAlreadyExistsException {
        if (isFileExist(file)) {
            throw new FileAlreadyExistsException("File with name: " + file.getFileName() + " already exists in Data base");
        } else {
            fileMapper.uploadFile(file);
        }
    }

    public Boolean isFileExist(File file) {
        File existingFile = fileMapper.getFileByName(file.getFileName());
        return existingFile != null;
    }

    public File getFileById(Integer fileId) {
        return fileMapper.getFile(fileId);
    }

    public void deleteFileById(Integer fileId) {
        fileMapper.deleteFile(fileId);
    }
}
