package com.lukaszkolmer.jobsportal.jobs.controller;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobOfferRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class JobsController {

    @GetMapping({"/jobs","jobs.html"})
    public String getJobs(){
        return "jobs";
    }

    @GetMapping("JobDetails.html") // inaczej nie działa, duże znaki w adresie?
    public String getJobsDetails(Model model, Long id){
        JobOfferRepository repository = JobOfferRepository.getInstance();
        id = 1L; //tymczasowe, zmienic pozniej na @param id
        JobDetails jobDetails = repository.findOfferById(1L);
        model.addAttribute("jobOffer",jobDetails);

        return "job_details";
    }
}
