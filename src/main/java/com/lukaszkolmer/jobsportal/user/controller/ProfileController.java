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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/profile/changepassword")
    public String getChangePasswordPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepository.findByUsername(auth.getName()));
        return "changepassword";
    }

    @PostMapping("/profile/changepassword")
    public String postChangePassword(@RequestParam String currentPassword,@RequestParam String newPassword,Model model,RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToChange =  userRepository.findByUsername(auth.getName());
        if (userToChange.getPassword().equals(currentPassword)) {
            userRepository.changeUserPassword(userToChange.getId(), newPassword);
            return "redirect:/profile/changepasswordsuccess";
        }
        else {
            redirectAttributes.addAttribute("message","Incorrect current password.");
            return "redirect:/profile/changepasswordfail";
        }
    }
    @GetMapping("/profile/changepasswordsuccess")
    public String getChangePasswordSuccessPage(Model model){
        return "changepasswordsuccess";
    }
    @GetMapping("/profile/changepasswordfail")
    public String getChangePasswordFailPage(Model model){
        return "changepasswordfail";
    }

    @GetMapping("/profile/changeemail")
    public String getChangeEmailPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepository.findByUsername(auth.getName()));

        return "changeemail";
    }
    @PostMapping("/profile/changeemail")
    public String postChangeEmail(@RequestParam String newEmail){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToChange =  userRepository.findByUsername(auth.getName());
        userRepository.changeUserEmail(userToChange.getId(),newEmail);
        return "redirect:/logout";
    }


    @GetMapping("/profile/joboffers")
    public String getPostedJobOffersPage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<JobDetails> listOfJobOffers = jobDetailsRepository.findOffersByOwner(auth.getName());
        model.addAttribute("listOfJobOffers",listOfJobOffers);
        return "showalluserjoboffers";
    }
}
