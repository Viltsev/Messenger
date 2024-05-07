package com.engmes.EnglishMessenger.Interests.services;

import com.engmes.EnglishMessenger.Interests.model.Interest;
import com.engmes.EnglishMessenger.Testing.model.Question;

import java.util.List;

public interface InterestService {
    List<Interest> getAllInterests();

    void saveInterest(Interest interest);

    List<Interest> getUserInterests(String email);
}
