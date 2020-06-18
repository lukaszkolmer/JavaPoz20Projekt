package com.lukaszkolmer.jobsportal.jobs.controller;

import com.lukaszkolmer.jobsportal.jobs.exceptions.NoOfferOfGivenID;
import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.services.JobDetailsRepositoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
public class JobsController {

    @Autowired
    JobDetailsRepositoryServices jobDetailsRepository;
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping({"/jobs", "jobs.html"})
    public String getJobs(Model model) {
        List<JobDetails> listOfJobOffers = jobDetailsRepository.getJobDetailsRepository().findAll();
        model.addAttribute("listOfJobOffers", listOfJobOffers);
        return "jobs";
    }

    @GetMapping("JobDetails.html") // inaczej nie działa, duże znaki w adresie?
    public String getJobsDetails(Model model, @RequestParam Long id) {

        JobDetails jobDetails = jobDetailsRepository.findOfferById(id);
        model.addAttribute("jobOffer", jobDetails);

        return "job_details";
    }

    @GetMapping("/jobs/addnew")
    public String getAddNewJobOffer(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        LocalDate currentDate = LocalDate.now();
        model.addAttribute("jobDetails", new JobDetails());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("currentDate", currentDate);
        return "addjoboffer";
    }

    @RequestMapping(value = "/jobs/addnew", method = RequestMethod.POST)
    public String addNewJobOffer(@ModelAttribute(name = "jobDetails") JobDetails jobDetails) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        LocalDate currentDate = LocalDate.now();
        if (jobDetails.getOwner().isEmpty()) {
            jobDetails.setOwner(userDetails.getUsername());
        }
        if (jobDetails.getPublishDate() == null) {
            jobDetails.setPublishDate(currentDate);
        }
        jobDetailsRepository.addNewJobOffer(jobDetails);

        return "jobofferadded";
    }

    @RequestMapping(path = "/jobs/deactivate/{id}", method = RequestMethod.GET)
    public String deactivateJobOffer(@PathVariable("id") Long id) throws NoOfferOfGivenID {
        JobDetails jobDetails = jobDetailsRepository.findOfferById(id);
        jobDetailsRepository.deactivateJobOffer(id);
        System.out.println("deactivated: " + jobDetails.getId());
        return "redirect:/profile/joboffers";
    }

    @GetMapping("/jobs/jobofferadded")
    public String getJobOfferAddedPage() {
        return "jobofferadded";
    }
}
