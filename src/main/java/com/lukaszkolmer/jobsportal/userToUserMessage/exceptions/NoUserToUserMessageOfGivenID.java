package com.lukaszkolmer.jobsportal.userToUserMessage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserToUserMessageOfGivenID extends RuntimeException {
    public NoUserToUserMessageOfGivenID(Long id) {
        super("Could not find offer of id: " + id);
    }
}
