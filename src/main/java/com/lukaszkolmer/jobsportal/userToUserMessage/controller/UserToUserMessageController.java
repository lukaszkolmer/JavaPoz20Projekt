package com.lukaszkolmer.jobsportal.userToUserMessage.controller;


import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.services.UserRepositoryServices;
import com.lukaszkolmer.jobsportal.userToUserMessage.model.UserToUserMessage;
import com.lukaszkolmer.jobsportal.userToUserMessage.services.UserToUserMessageServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class UserToUserMessageController {

    @Autowired
    UserRepositoryServices userRepositoryServices;
    @Autowired
    UserToUserMessageServices userToUserMessageServices;


    @GetMapping("/u2umessage")
    public String getUserToUserMessagePage(Model model, @RequestParam Long id) {
        User receiver = userRepositoryServices.findUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userRepositoryServices.findByUsername(auth.getName());

        model.addAttribute("userToUserMessage", new UserToUserMessage());
        model.addAttribute("sender", sender);
        model.addAttribute("receiver", receiver);
        return "usertousermessagesend";
    }

    @PostMapping("u2umessage")
    public String postToUserToUserMessagePage(@RequestParam Long userid, @ModelAttribute(name = "userToUserMessage") UserToUserMessage userToUserMessage) {
        User receiver = userRepositoryServices.findUserById(userid);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userRepositoryServices.findByUsername(auth.getName());

        userToUserMessage.setReceivedDate(LocalDate.now());
        userToUserMessage.setSentDate(LocalDate.now());
        userToUserMessage.setSender(sender.username);
        userToUserMessage.setReceiver(receiver.username);

        userToUserMessageServices.addNewUserToUserMessage(userToUserMessage);

        System.out.println(userToUserMessage);
        return "messagesentsuccess";
    }

}
