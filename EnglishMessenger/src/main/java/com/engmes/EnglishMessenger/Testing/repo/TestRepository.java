package com.engmes.EnglishMessenger.Testing.repo;

import com.engmes.EnglishMessenger.Testing.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Question, Long> {
}