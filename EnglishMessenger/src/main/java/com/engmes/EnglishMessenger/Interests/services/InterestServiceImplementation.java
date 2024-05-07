package com.engmes.EnglishMessenger.Interests.services;

import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Interests.repo.InterestRepository;
import com.engmes.EnglishMessenger.Profile.controllers.RegisterController;
import com.engmes.EnglishMessenger.Profile.model.User;
import com.engmes.EnglishMessenger.Profile.repository.UserRepository;
import com.engmes.EnglishMessenger.Profile.services.UserService;
import com.engmes.EnglishMessenger.Testing.model.Question;
import com.engmes.EnglishMessenger.Testing.repo.TestRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class InterestServiceImplementation implements InterestService {
    private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
    private final InterestRepository interestRepository;

    @Autowired
    private final UserService userService;

    @Override
    public List<Interest> getAllInterests() {
        return interestRepository.findAll();
    }

    @Override
    public void saveInterest(Interest interest) {
        interestRepository.save(interest);
    }

    @Override
    public List<Interest> getUserInterests(String email) {
        Optional<User> user = userService.findByEmail(email);

        if (user.isPresent()) {
            User currentUser = user.get();
            List<Long> userIdInterests = currentUser.getIdInterests();
            return interestRepository.findAllById(userIdInterests);
        }
        else {
            return null;
        }
    }
}
