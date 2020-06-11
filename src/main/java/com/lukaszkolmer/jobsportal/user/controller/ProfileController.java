package com.lukaszkolmer.jobsportal.user.controller;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobDetailsRepositoryImpl;
import com.lukaszkolmer.jobsportal.security.UserDetailsService;
import com.lukaszkolmer.jobsportal.security.UserPrincipal;
import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserRepositoryImpl userRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JobDetailsRepositoryImpl jobDetailsRepository;

    @GetMapping("/profile")
    public String getProfileSettings(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        model.addAttribute("user", userRepository.findByUsername(auth.getName()) );
        model.addAttribute("userDetails",userDetails);
        return "profile";
    }

    @GetMapping("/profile/joboffers")
    public String getPostedJobOffersPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<JobDetails> listOfJobOffers = jobDetailsRepository.findOffersByOwner(auth.getName());
        model.addAttribute("listOfJobOffers",listOfJobOffers);
        return "showalluserjoboffers";
    }
}
