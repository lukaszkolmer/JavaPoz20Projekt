package com.lukaszkolmer.jobsportal.contact.controller;

import com.lukaszkolmer.jobsportal.contact.model.CompanyInfo;
import com.lukaszkolmer.jobsportal.contact.model.UserMessage;
import com.lukaszkolmer.jobsportal.contact.repository.UserMessageRepositoryImpl;
import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ContactController {

    @Autowired
    UserMessageRepositoryImpl userMessageRepositoryImpl;

    @GetMapping({"/contact","/contact.html"})
    public String getContact(Model model){
        CompanyInfo companyInfo = CompanyInfo.getInstance();
        model.addAttribute("CompanyInfo",companyInfo);
        return "contact";
    }

    @GetMapping("/contact/messages")
    public String showMessages(Model model){
        List<UserMessage> messageList = userMessageRepositoryImpl.getUserMessageRepository().findAll();
        model.addAttribute("messageList", messageList);
        return "contactmessage";
    }

    @PostMapping({"/contact","/contact.html"})
    public UserMessage addNewMessage(HttpServletRequest request){
        //NOT WORKING :(
        String name = request.getParameter("name");
        String subject = request.getParameter("subject");
        String message = request.getParameter("message");
        String email = request.getParameter("email");
        UserMessage userMessage = new UserMessage(name,email,subject,message);
        userMessageRepositoryImpl.addNewUserMessage(userMessage);
        System.out.println("added new massage");
        return userMessage;
    }
}
