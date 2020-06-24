package com.lukaszkolmer.jobsportal.user.controller;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.services.JobDetailsRepositoryServices;
import com.lukaszkolmer.jobsportal.security.UserDetailsService;
import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.services.UserRepositoryServices;
import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import com.lukaszkolmer.jobsportal.userToUserMessage.services.UserToUserMessageServices;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProfileController {

    @Autowired
    UserRepositoryServices userRepository;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JobDetailsRepositoryServices jobDetailsRepository;
    @Autowired
    UserToUserMessageServices userToUserMessageServices;

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
        if (userToChange.getPassword().equals(currentPassword) && !userToChange.getPassword().equals(newPassword)) {
            userRepository.changeUserPassword(userToChange.getId(), newPassword);
            redirectAttributes.addFlashAttribute("passwordChangeMessage", "Password change successful");
        } else if (userToChange.getPassword().equals(newPassword)) {
            redirectAttributes.addFlashAttribute("passwordChangeMessage", "New password is the same as old one.");
        } else {
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
    public String postChangeEmail(@RequestParam String newEmail, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userToChange = userRepository.findByUsername(auth.getName());
        userRepository.changeUserEmail(userToChange.getId(), newEmail);
        redirectAttributes.addFlashAttribute("changeemailmessage", "Email change successful");
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

    @GetMapping("/profile/mailbox")
    public String getMailboxPage() {
        return "mailbox";
    }

    @GetMapping("/profile/mailbox/outbox")
    public String getMailboxSentPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        List<UserToUserMessage> messages = userToUserMessageServices.findUserToUserMessageBySender(user.username);
        model.addAttribute("user", user);
        model.addAttribute("usertousermessage", new UserToUserMessage());
        model.addAttribute("sentmessages", messages);
        return "sentmessages";
    }

    @GetMapping("/profile/mailbox/inbox")
    public String getMailboxReceivedPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(auth.getName());
        List<UserToUserMessage> messages = userToUserMessageServices.findUserToUserMessageByReceiver(user.username);
        model.addAttribute("user", user);
        model.addAttribute("usertousermessage", new UserToUserMessage());
        model.addAttribute("receivedMessages", messages);
        return "receivedmessages";
    }

    @GetMapping("/profile/mailbox/outbox/{id}")
    public String getSentMessagePage(Model model, @PathVariable("id") Long id) {
        UserToUserMessage message = userToUserMessageServices.findUserToUserMessageById(id);
        model.addAttribute("message", message);
        return "usertousermessageview";
    }

    @GetMapping("/profile/mailbox/inbox/{id}")
    public String getReceivedMessagePage(Model model, @PathVariable("id") Long id) {
        UserToUserMessage message = userToUserMessageServices.findUserToUserMessageById(id);
        if (!message.isAlreadyRead()) {
            userToUserMessageServices.markMessageAsAlreadyRead(id);
        }
        model.addAttribute("message", message);
        return "usertousermessageview";
    }

    @GetMapping("/profile/mailbox/inbox/{id}/downloadatt")
    public void downloadAttachmentFromInboxMessage(@PathVariable("id") Long id, @RequestParam("filename") String filename,
                                                   HttpServletResponse response) throws IOException {
        UserToUserMessage message = userToUserMessageServices.findUserToUserMessageById(id);


    }
}
