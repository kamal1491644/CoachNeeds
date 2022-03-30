package com.code.review.CodeReview.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class AppController {
    @GetMapping
    public ResponseEntity<Object> getUsers(@RequestHeader HttpHeaders httpHeaders) {
        return ResponseEntity.ok().body("Hi user!");
    }


}
