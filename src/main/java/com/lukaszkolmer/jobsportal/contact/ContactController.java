package com.lukaszkolmer.jobsportal.contact;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactController {
    @GetMapping({"/contact","/contact.html"})
    public String getContact(){
        return "contact";
    }
}
