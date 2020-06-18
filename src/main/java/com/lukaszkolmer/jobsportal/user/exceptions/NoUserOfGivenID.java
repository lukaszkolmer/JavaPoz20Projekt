package com.lukaszkolmer.jobsportal.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoUserOfGivenID extends RuntimeException {
    public NoUserOfGivenID(Long id) {
        super("Could not find user of id: " + id);
    }
}
