package com.lukaszkolmer.jobsportal.jobs.services;

import com.lukaszkolmer.jobsportal.jobs.exceptions.NoOfferOfGivenID;
import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobDetailsRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Data
public class JobDetailsRepositoryServices {

    private JobDetailsRepository jobDetailsRepository;

    @Autowired
    public JobDetailsRepositoryServices(JobDetailsRepository jobDetailsRepository) {
        this.jobDetailsRepository = jobDetailsRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void setDatabase() {
        JobDetails testJobDetails = new JobDetails("IT", "Test offer", "Test descritpion", "Test resposibilities", "Test qualifications",
                "Test benefits", "Test location",
                "Test 0 - 9001 salary", "Test vacancy",
                LocalDate.of(2000, 1, 1), "Test job nature", "admin");
        JobDetails testJobDetails2 = new JobDetails("Administration", "Test2 offer", "Test2 descritpion", "Test2 resposibilities", "Test2 qualifications",
                "Test2 benefits", "Test2 location",
                "Test2 0 - 9001 salary", "Test2 vacancy",
                LocalDate.of(1995, 11, 11), "Test2 job nature", "admin");
        JobDetails testJobDetails3 = new JobDetails("Teaching", "Test3 offer", "Test3 descritpion", "Test3 resposibilities", "Test3 qualifications",
                "Test3 benefits", "Test3 location",
                "Test3 0 - 9001 salary", "Test3 vacancy"
                , LocalDate.of(1889, 12, 31), "Test3 job nature", "admin");
        jobDetailsRepository.save(testJobDetails);
        jobDetailsRepository.save(testJobDetails2);
        jobDetailsRepository.save(testJobDetails3);
    }

    public JobDetails findOfferById(Long id) {
        return jobDetailsRepository.findById(id).orElseThrow(() -> new NoOfferOfGivenID(id));
    }

    public List<JobDetails> findOffersByCategory(String category) {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.category.equals(category)).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersByOwner(String owner) {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.owner.equals(owner)).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersByLocation(String location) {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.location.equals(location)).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersByJobNature(String jobNature) {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.jobNature.equals(jobNature)).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersByQualification(String qualification) {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.qualifications.equals(qualification)).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersIsActive() {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> jobDetails.isActive).collect(Collectors.toList());
    }

    public List<JobDetails> findOffersIsNotActive() {
        return jobDetailsRepository.findAll().stream().filter(jobDetails -> !jobDetails.isActive).collect(Collectors.toList());
    }

    public JobDetails addNewJobOffer(JobDetails jobOfferToAdd) {
        jobDetailsRepository.save(jobOfferToAdd);
        return jobOfferToAdd;
    }

    public JobDetails deactivateJobOffer(Long id) {
        JobDetails jobToDeactivate = jobDetailsRepository.getOne(id);
        jobToDeactivate.isActive = false;
        jobDetailsRepository.save(jobToDeactivate);
        return jobToDeactivate;
    }

    public JobDetails activateJobOffer(Long id) {
        JobDetails jobToActivate = jobDetailsRepository.getOne(id);
        jobToActivate.isActive = true;
        jobDetailsRepository.save(jobToActivate);
        return jobToActivate;
    }

    public JobDetails removeJobOffer(JobDetails offerToRemove) {
        jobDetailsRepository.delete(offerToRemove);
        return offerToRemove;
    }

}
