package com.engmes.EnglishMessenger.Testing.services;

import com.engmes.EnglishMessenger.Testing.model.Question;

import java.util.List;

public interface TestService {
    void saveQuestion(Question question);
    List<Question> getAllQuestions();
    Question findQuestionById(Long id);
}
