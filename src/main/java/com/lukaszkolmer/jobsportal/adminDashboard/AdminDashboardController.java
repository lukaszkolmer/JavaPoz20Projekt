package com.lukaszkolmer.jobsportal.adminDashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminDashboardController {

    @GetMapping("/admindashboard")
    public String getAdminDashboardPage() {

        return "admindashboard";
    }

}
