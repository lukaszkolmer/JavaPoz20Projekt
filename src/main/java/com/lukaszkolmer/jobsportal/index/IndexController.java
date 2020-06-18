package com.lukaszkolmer.jobsportal.index;

import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.services.JobDetailsRepositoryServices;
import com.lukaszkolmer.jobsportal.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

//html 56, rozwijane menu "pages" do zrobienia

    @Autowired
    JobDetailsRepositoryServices jobDetailsRepository;
    @Autowired
    UserDetailsService userDetailsService;

    @GetMapping({"/", "/index.html"})
    public String getIndex(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List<JobDetails> listOfJobOffers = jobDetailsRepository.getJobDetailsRepository().findAll();
        model.addAttribute("listOfJobOffers", listOfJobOffers);
        model.addAttribute("user", new User());
        return "index";
    }
}
