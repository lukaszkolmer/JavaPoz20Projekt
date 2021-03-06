package com.lukaszkolmer.jobsportal.contact.controller;

import com.lukaszkolmer.jobsportal.contact.model.CompanyInfo;
import com.lukaszkolmer.jobsportal.contact.model.UserMessage;
import com.lukaszkolmer.jobsportal.contact.services.UserMessageRepositoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class ContactController {

    @Autowired
    UserMessageRepositoryServices userMessageRepositoryServices;

    @GetMapping({"/contact", "/contact.html"})
    public String getContact(Model model) {
        CompanyInfo companyInfo = CompanyInfo.getInstance();
        model.addAttribute("CompanyInfo", companyInfo);
        model.addAttribute("userMessage", new UserMessage());
        return "contact";
    }

    @GetMapping("/contact/messages")
    public String showMessages(Model model) {
        List<UserMessage> messageList = userMessageRepositoryServices.getUserMessageRepository().findAll();
        model.addAttribute("messageList", messageList);
        return "contactmessage";
    }

    @RequestMapping(value = "/contact/addmsg", method = RequestMethod.POST)
    public String addNewUserMessage(@ModelAttribute(name = "userMessage") UserMessage userMessage) {
        userMessageRepositoryServices.addNewUserMessage(userMessage);
        return "messagesentsuccess";
    }
}
