package com.lukaszkolmer.jobsportal.attachment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoAttachmentOfGivenID extends RuntimeException {
    public NoAttachmentOfGivenID(Long id) {
        super("Could not find message of id: " + id);
    }
}
