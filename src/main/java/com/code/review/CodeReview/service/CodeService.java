package com.code.review.CodeReview.service;

import com.code.review.CodeReview.model.Code;
import com.code.review.CodeReview.repository.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CodeService {
    @Autowired
    private CodeRepository codeRepository;

    public List<Code> getAllCodes(){
        return codeRepository.findAll();
    }
}
