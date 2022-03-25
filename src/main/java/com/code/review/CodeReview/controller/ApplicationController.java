package com.code.review.CodeReview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {
    @GetMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }
}
