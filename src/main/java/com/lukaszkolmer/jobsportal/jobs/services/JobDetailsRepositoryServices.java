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
        JobDetails testJobDetails = new JobDetails("IT", "Java junior developer", "We are looking for promising new Java devs to join our small team!", "- Working on high-volume, low-latency applications for mission-critical systems <br> - Writing well designed, testable, efficient code <br>" ,
                "- Basic experience in Java <br> - Critical thinking and team player",
                "-Private healthcare <br> - Relocation package", "Poznan",
                "3000 - 4000", "1",
                LocalDate.of(2020, 8, 15), "Full time", "admin");
        JobDetails testJobDetails2 = new JobDetails("Administration", "Office manager needed.", "Looking for great and experienced office manager to manage our office.",
                "- Managing office <br>- Visitors reception <br> - Taking care of the circulation of documents", "- Being organized  <br> - Good mannered and communicative person",
                "Private, well equipped office", "Warsaw",
                " 4500 - 7000", "1",
                LocalDate.of(2020, 7, 19), "Full time", "admin");
        JobDetails testJobDetails3 = new JobDetails("Cleaning", "Looking for cleaner!", "Looking for a person for cleaning my house 2 times a week.", "Complex cleaning of whole house in given time 2 times a week. ", "Good physical condition",
                "Salary paid each week", "Poznan",
                "To negotiation", "1"
                , LocalDate.of(2020, 9, 1), "Part time", "user");
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
