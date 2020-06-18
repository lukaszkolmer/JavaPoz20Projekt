package com.lukaszkolmer.jobsportal.contact.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoMessageOfGivenID extends RuntimeException {
    public NoMessageOfGivenID(Long id) {
        super("Could not find message of id: " + id);
    }
}
