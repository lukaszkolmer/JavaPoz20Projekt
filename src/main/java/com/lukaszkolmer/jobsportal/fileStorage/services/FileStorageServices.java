package com.lukaszkolmer.jobsportal.fileStorage.services;

import com.lukaszkolmer.jobsportal.fileStorage.exceptions.FileNotFoundException;
import com.lukaszkolmer.jobsportal.fileStorage.exceptions.FileStorageException;
import com.lukaszkolmer.jobsportal.fileStorage.model.File;
import com.lukaszkolmer.jobsportal.fileStorage.repository.FileStorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileStorageServices {

    @Autowired
    private FileStorageRepository fileStorageRepository;

    public File storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            File fileToStore = new File(fileName, file.getContentType(), file.getBytes());

            return fileStorageRepository.save(fileToStore);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public File getFile(String fileId) {
        return fileStorageRepository.findById(fileId)
                .orElseThrow(() -> new FileNotFoundException("File not found with id " + fileId));
    }
}
