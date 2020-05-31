package com.lukaszkolmer.jobsportal.jobs.controller;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobDetailsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class JobsController {

    @Autowired
    JobDetailsRepositoryImpl jobDetailsRepository;

    @GetMapping({"/jobs","jobs.html"})
    public String getJobs(Model model){
        List<JobDetails> listOfJobOffers = jobDetailsRepository.getJobDetailsRepository().findAll();
        model.addAttribute("listOfJobOffers",listOfJobOffers);
        return "jobs";
    }

    @GetMapping("JobDetails.html") // inaczej nie działa, duże znaki w adresie?
    public String getJobsDetails(Model model, @RequestParam Long id){

        JobDetails jobDetails = jobDetailsRepository.findOfferById(id); // chwilowo działa jeszcze na sztywno podawanym id.
        model.addAttribute("jobOffer",jobDetails);

        return "job_details";
    }
}
