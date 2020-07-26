package com.lukaszkolmer.jobsportal.jobs.controller;

import com.lukaszkolmer.jobsportal.attachment.model.Attachment;
import com.lukaszkolmer.jobsportal.attachment.services.AttachmentRepositoryImpl;
import com.lukaszkolmer.jobsportal.jobs.exceptions.NoOfferOfGivenID;
import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.services.JobDetailsRepositoryServices;
import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.services.UserRepositoryServices;
import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import com.lukaszkolmer.jobsportal.userToUserMessage.services.UserToUserMessageServices;
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
    @Autowired
    UserRepositoryServices userRepositoryServices;
    @Autowired
    UserToUserMessageServices userToUserMessageServices;
    @Autowired
    AttachmentRepositoryImpl attachmentRepository;

    @GetMapping({"/jobs", "jobs.html"})
    public String getJobs(Model model) {
        List<JobDetails> listOfJobOffers = jobDetailsRepository.getJobDetailsRepository().findAll();
        model.addAttribute("listOfJobOffers", listOfJobOffers);
        return "jobs";
    }

    @GetMapping("JobDetails.html")
    public String getJobsDetails(Model model, @RequestParam Long id) {
        JobDetails jobDetails = jobDetailsRepository.findOfferById(id);
        User receiver = userRepositoryServices.findByUsername(jobDetails.getOwner());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userRepositoryServices.findByUsername(auth.getName());
        if (attachmentRepository.findAttachmentOfUser(sender.getUsername())!=null)
        {
            Attachment attachment = attachmentRepository.findAttachmentOfUser(sender.getUsername());
            model.addAttribute("attachment", attachment);
        }
        else {
            model.addAttribute("attachment", null);
        }
        model.addAttribute("userToUserMessage", new UserToUserMessage());
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);



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
