package com.lukaszkolmer.jobsportal.user.controller;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobDetailsRepositoryImpl;
import com.lukaszkolmer.jobsportal.security.UserDetailsService;
import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getProfileSettings(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = userDetailsService.loadUserByUsername(auth.getName());
        model.addAttribute("user", userRepository.findByUsername(auth.getName()));
        model.addAttribute("userDetails", userDetails);
        return "profile";
    }

    @GetMapping("/profile/view/{id}")
    public String getViewProfilePage(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User viewingUser = userRepository.findByUsername(auth.getName());
        User userToView = userRepository.findUserById(id);
        List<JobDetails> listOfJobOffers = jobDetailsRepository.findOffersByOwner(userToView.username);
        model.addAttribute("listOfJobOffers", listOfJobOffers);
        model.addAttribute("userToView", userToView);
        model.addAttribute("viewingUser", viewingUser);
        return "viewprofile";
    }


    @GetMapping("/profile/changepassword")
    public String getChangePasswordPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepository.findByUsername(auth.getName()));
        return "changepassword";
    }

    @PostMapping("/profile/changepassword")
    public String postChangePassword(@RequestParam String currentPassword, @RequestParam String newPassword, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToChange = userRepository.findByUsername(auth.getName());
        if (userToChange.getPassword().equals(currentPassword)&&!userToChange.getPassword().equals(newPassword)) {
            userRepository.changeUserPassword(userToChange.getId(), newPassword);
            redirectAttributes.addFlashAttribute("passwordChangeMessage","Password change successful");
        }
        else if (userToChange.getPassword().equals(newPassword)) {
            redirectAttributes.addFlashAttribute("passwordChangeMessage","New password is the same as old one.");
        }
        else {
            redirectAttributes.addFlashAttribute("passwordChangeMessage", "Incorrect current password.");
        }
        return "redirect:/profile/" + "changepassword";
    }


    @GetMapping("/profile/changeemail")
    public String getChangeEmailPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", userRepository.findByUsername(auth.getName()));

        return "changeemail";
    }

    @PostMapping("/profile/changeemail")
    public String postChangeEmail(@RequestParam String newEmail,RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToChange = userRepository.findByUsername(auth.getName());
        userRepository.changeUserEmail(userToChange.getId(), newEmail);
        redirectAttributes.addFlashAttribute("changeemailmessage","Email change successful");
        return "redirect:/profile/" + "changeemail";
    }


    @GetMapping("/profile/joboffers")
    public String getPostedJobOffersPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<JobDetails> listOfJobOffers = jobDetailsRepository.findOffersByOwner(auth.getName());
        model.addAttribute("listOfJobOffers", listOfJobOffers);
        return "showalluserjoboffers";
    }

    @GetMapping("/register")
    public String getRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String postRegister(@ModelAttribute(name = "user") User user, RedirectAttributes redirectAttributes) {
        if (userRepository.checkIfUserOfGivenUsernameAlreadyExist(user) && userRepository.checkIfUserOfGivenEmailAlreadyExist(user)) {
            redirectAttributes.addFlashAttribute("usernameAndEmailError", "Username already taken! \n Email already taken!");
            return "redirect:/register";
        }
        if (userRepository.checkIfUserOfGivenUsernameAlreadyExist(user)) {
            redirectAttributes.addFlashAttribute("usernameError", "User of given username already exists!");
            return "redirect:/register";
        }
        if (userRepository.checkIfUserOfGivenEmailAlreadyExist(user)) {
            redirectAttributes.addFlashAttribute("emailError", "User with given email already exists!");
            return "redirect:/register";
        }
        if (!userRepository.checkIfUserOfGivenUsernameAlreadyExist(user) && !userRepository.checkIfUserOfGivenEmailAlreadyExist(user)) {
            user.setRole("USER");
            userRepository.addNewUser(user);
            return "redirect:/";
        }
        return "register";
    }
}
