package com.engmes.EnglishMessenger.Interests.services;

import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Interests.repo.InterestRepository;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Testing.model.Question;
import com.engmes.EnglishMessenger.Testing.repo.TestRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class InterestServiceImplementation implements InterestService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final InterestRepository interestRepository;

    @Override
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    @Override
    public void saveInterest(Interest interest) {
        interestRepository.save(interest);
    }
}
