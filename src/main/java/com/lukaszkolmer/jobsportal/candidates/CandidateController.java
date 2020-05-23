package com.lukaszkolmer.jobsportal.candidates;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CandidateController {

        @GetMapping({"/candidate","/candidate.html"})
        public String getContact(){
            return "candidate";
        }
}
