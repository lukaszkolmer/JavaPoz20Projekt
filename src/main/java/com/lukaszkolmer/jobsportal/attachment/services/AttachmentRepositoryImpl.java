package com.lukaszkolmer.jobsportal.attachment.services;

import com.lukaszkolmer.jobsportal.attachment.exceptions.NoAttachmentOfGivenID;
import com.lukaszkolmer.jobsportal.attachment.model.Attachment;
import com.lukaszkolmer.jobsportal.attachment.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentRepositoryImpl {

    AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentRepositoryImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }


    public Attachment addNewAttachment(Attachment attachment) {
        attachmentRepository.save(attachment);
        return attachment;
    }

    public Attachment findAttachmentById(Long id) {
        return attachmentRepository.findById(id).orElseThrow(() -> new NoAttachmentOfGivenID(id));
    }
    public Attachment findAttachmentByUrl(String url) {
        return attachmentRepository.findAll().stream().filter(attachment -> attachment.getUrl().equals(url)).findAny().orElse(null);
    }
    public Attachment findAttachmentOfUser(String owner) {
        return attachmentRepository.findAll().stream().filter(attachment -> attachment.getOwnerUsername().equals(owner)).findAny().orElse(null);
    }


}
