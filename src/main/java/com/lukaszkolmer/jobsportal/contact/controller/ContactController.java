package com.lukaszkolmer.jobsportal.contact.controller;

import com.lukaszkolmer.jobsportal.contact.model.CompanyInfo;
import com.lukaszkolmer.jobsportal.contact.model.UserMessage;
import com.lukaszkolmer.jobsportal.contact.repository.UserMessageRepositoryImpl;
import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    UserMessageRepositoryImpl userMessageRepositoryImpl;

    @GetMapping({"/contact","/contact.html"})
    public String getContact(Model model){
        CompanyInfo companyInfo = CompanyInfo.getInstance();
        model.addAttribute("CompanyInfo",companyInfo);
        model.addAttribute("userMessage",new UserMessage());
        return "contact";
    }

    @GetMapping("/contact/messages")
    public String showMessages(Model model){
        List<UserMessage> messageList = userMessageRepositoryImpl.getUserMessageRepository().findAll();
        model.addAttribute("messageList", messageList);
        return "contactmessage";
    }

    @RequestMapping(value = "/contact/addmsg",method = RequestMethod.POST)
    public String addNewUserMessage(@ModelAttribute(name = "userMessage") UserMessage userMessage){
       userMessageRepositoryImpl.addNewUserMessage(userMessage);
        return "messagesentsuccess";
    }
}
