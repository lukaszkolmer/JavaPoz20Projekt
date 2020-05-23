package com.lukaszkolmer.jobsportal.blog;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BlogController {

    @GetMapping({"/blog", "/blog.html"})
    public String getBlog() {
        return "blog";
    }


    @GetMapping({"/single-blog", "/single-blog.html"})
    public String getSingleBlog() {
        return "single-blog";
    }
}

