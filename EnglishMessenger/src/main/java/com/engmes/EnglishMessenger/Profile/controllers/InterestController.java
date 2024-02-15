package com.engmes.EnglishMessenger.Profile.controllers;


import com.engmes.EnglishMessenger.Profile.model.Interest;
import com.engmes.EnglishMessenger.Profile.repository.InterestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InterestController {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    @Autowired
    private InterestRepository interestRepository;

    @GetMapping("/interests")
    public List<Interest> getInterests() {
        logger.info("Список интересов");
        return interestRepository.findAll();
    }
}
