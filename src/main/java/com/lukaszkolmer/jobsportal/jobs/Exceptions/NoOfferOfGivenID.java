package com.lukaszkolmer.jobsportal.jobs.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoOfferOfGivenID extends RuntimeException {
   public NoOfferOfGivenID(Long id) {
        super("Could not find offer of id: " + id);
    }
}
