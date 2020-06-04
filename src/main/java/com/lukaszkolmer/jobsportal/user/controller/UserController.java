package com.lukaszkolmer.jobsportal.user.controller;

import com.lukaszkolmer.jobsportal.user.model.User;
import com.lukaszkolmer.jobsportal.user.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    UserRepositoryImpl userRepository;


    @GetMapping("/profile-settings")
    public String getProfileSettings() {
        return "";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        List<User> userList = userRepository.getUserRepository().findAll();
        model.addAttribute("user", new User());
        model.addAttribute("userList", userList);
        return "login";
    }

    @PostMapping("/login")
    public String loginCheck(@ModelAttribute(name = "user") User user) {
        for (User userInBase : userRepository.getUserRepository().findAll()) {
            if (user.username.equals(userInBase.username)){
                if (user.password.equals(userInBase.password)){

                    return "logingoodcredentials";
                }
            }

        }
        return "loginbadcredentials";
    }
}
