package com.engmes.EnglishMessenger.Testing.services;

import com.engmes.EnglishMessenger.Testing.model.Question;
import com.engmes.EnglishMessenger.Testing.repo.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class TestServiceImplementation implements TestService {
    private final TestRepository repository;

    @Override
    public void saveQuestion(Question question) {
        repository.save(question);
    }

    @Override
    public List<Question> getAllQuestions() {
        return repository.findAll();
    }

    @Override
    public Question findQuestionById(Long id) {
        return repository.getReferenceById(id);
    }
}
