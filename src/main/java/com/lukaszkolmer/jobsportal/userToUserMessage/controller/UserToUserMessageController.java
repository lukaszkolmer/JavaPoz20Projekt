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
        return "usertousermessage";
    }

    @PostMapping("u2umessage")
    public String postToUserToUserMessagePage(Model model, @RequestParam Long id,
                                              @ModelAttribute(name = "userToUserMessage") UserToUserMessage userToUserMessage) {
        User receiver = userRepositoryServices.findUserById(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User sender = userRepositoryServices.findByUsername(auth.getName());
        userToUserMessage.setSender(sender.username);
        userToUserMessage.setReceiver(receiver.username);
        userToUserMessageServices.addNewUserToUserMessage(userToUserMessage);
        return "messagesentsuccess";
    }

}
