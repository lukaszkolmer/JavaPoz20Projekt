package com.lukaszkolmer.jobsportal.login;

import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/logoutsuccess")
    public String getLogoutSuccessPage(){

        return "logoutsuccess";
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String addNewUserMessage(@ModelAttribute(name = "user") User user){
        return "loginsuccess";
    }
}
