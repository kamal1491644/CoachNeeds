package com.code.review.CodeReview.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ApplicationController {

    @GetMapping("/login")
    public String login(){
        return "index";
    }

    @GetMapping("/logout")
    public String logout(){
        return "index";
    }
}
