package com.lukaszkolmer.jobsportal.jobs.repository;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class JobOfferRepository {

    List<JobDetails> jobOffers = initialize();

    private static final JobOfferRepository instance = new JobOfferRepository();

    private JobOfferRepository(){}

    public static JobOfferRepository getInstance(){
        return instance;
    }

    public List<JobDetails> initialize(){
        JobDetails testJobOffer = new JobDetails(1L,"Test offer","Test descritpion","Test resposibilities","Test qualifications",
                "Test benefits", "Test location",
                "Test 0 - 9001 salary","Test vacancy", LocalDate.of(2000,1,1),"Test job nature");
        List<JobDetails> listToReturn = new ArrayList<>();
        listToReturn.add(testJobOffer);
        return  listToReturn;
    }
    private Long generateNextId() {
        return jobOffers.stream().mapToLong(JobDetails::getId).max().orElseThrow()+1;
    }
    public JobDetails findOfferById(Long id){

    return jobOffers.stream()
            .filter(JobDetails -> JobDetails.getId().equals(id))
            .findAny()
            .orElseThrow();
    }

}
