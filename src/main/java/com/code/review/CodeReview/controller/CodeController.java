package com.code.review.CodeReview.controller;

import com.code.review.CodeReview.model.Code;
import com.code.review.CodeReview.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/code")
public class CodeController {

    @Autowired
    private CodeService codeService;

    @GetMapping("/all")
    public ResponseEntity<List<Code>> getAllCodes() {
        List<Code> result = codeService.getAllCodes();
        return ResponseEntity.ok().body(result);
    }
}
