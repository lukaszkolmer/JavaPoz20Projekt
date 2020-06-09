package com.lukaszkolmer.jobsportal.login;

import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user", new User());
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return ("redirect:/profile");
        } else return "login";
    }

    @GetMapping("/logoutsuccess")
    public String getLogoutSuccessPage() {

        return "logoutsuccess";
    }
}
