package com.lukaszkolmer.jobsportal.jobs.repository;

import com.lukaszkolmer.jobsportal.jobs.Exceptions.NoOfferOfGivenID;
import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
@Data
public class JobDetailsRepositoryImpl {

    private JobDetailsRepository jobDetailsRepository;

    @Autowired
    public JobDetailsRepositoryImpl(JobDetailsRepository jobDetailsRepository) {
        this.jobDetailsRepository = jobDetailsRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setDatabase(){
        JobDetails testJobDetails = new JobDetails("Test offer","Test descritpion","Test resposibilities","Test qualifications",
                "Test benefits", "Test location",
                "Test 0 - 9001 salary","Test vacancy", LocalDate.of(2000,1,1),"Test job nature");
        jobDetailsRepository.save(testJobDetails);
    }
    public JobDetails findOfferById(Long id) {
        return jobDetailsRepository.findById(id).orElseThrow(() -> new NoOfferOfGivenID(id));
    }

    public JobDetails addNewJobOffer(JobDetails jobOfferToAdd){
        jobDetailsRepository.save(jobOfferToAdd);
        return jobOfferToAdd;
    }

    public JobDetails removeJobOffer(JobDetails offerToRemove){
        jobDetailsRepository.delete(offerToRemove);
        return offerToRemove;
    }
}
