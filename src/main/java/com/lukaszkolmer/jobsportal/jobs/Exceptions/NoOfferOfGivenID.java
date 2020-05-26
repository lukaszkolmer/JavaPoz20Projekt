package com.lukaszkolmer.jobsportal.jobs.Exceptions;

public class NoOfferOfGivenID extends RuntimeException {
   public NoOfferOfGivenID(Long id) {
        super("Could not find offer of id: " + id);
    }
}
