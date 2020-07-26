package com.lukaszkolmer.jobsportal.attachment.controller;

import com.lukaszkolmer.jobsportal.attachment.model.Attachment;
import com.lukaszkolmer.jobsportal.attachment.services.AttachmentRepositoryImpl;
import com.lukaszkolmer.jobsportal.awss3.services.S3ServicesImpl;
import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.services.UserRepositoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AttachmentController {


    @Autowired
    AttachmentRepositoryImpl attachmentRepository;
    @Autowired
    S3ServicesImpl s3Services;
    @Autowired
    UserRepositoryServices userRepositoryServices;

    @GetMapping("/fileupload")
    public String getFileUploadPage(Model model) {
        model.addAttribute("message", "");
        return "fileupload";
    }

    @PostMapping("/fileupload")
    public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User uploader = userRepositoryServices.findByUsername(auth.getName());

        // check if file is empty
        if (file.isEmpty()) {
            attributes.addFlashAttribute("message", "Please select a file to upload.");
            return "redirect:/fileupload";
        }
        String fileUrl = s3Services.uploadFile(file);
        Attachment attachment = new Attachment(file.getOriginalFilename(),fileUrl,uploader.getUsername());
        attachmentRepository.addNewAttachment(attachment);

        // return success response
        attributes.addFlashAttribute("message", "You successfully uploaded " + file.getName() + '!');

        return "redirect:/fileupload";
    }

}
