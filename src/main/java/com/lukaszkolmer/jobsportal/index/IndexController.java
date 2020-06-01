package com.lukaszkolmer.jobsportal.index;
import com.lukaszkolmer.jobsportal.jobs.model.JobDetails;
import com.lukaszkolmer.jobsportal.jobs.repository.JobDetailsRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

//html 56, rozwijane menu "pages" do zrobienia

    @Autowired
    JobDetailsRepositoryImpl jobDetailsRepository;

    @GetMapping({"/","/index.html"})
    public String getIndex(Model model){
        List<JobDetails> listOfJobOffers = jobDetailsRepository.getJobDetailsRepository().findAll();
        model.addAttribute("listOfJobOffers",listOfJobOffers);

        return "index";
    }
}
