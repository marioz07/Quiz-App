package com.Atharva.QuizApp.dao;

import com.Atharva.QuizApp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository <Question, Integer>{

    List<Question> findByCategory(String category);


    @Query(value = "SELECT * FROM question q where q.category=:category ORDER BY RANDOM() LIMIT :numsQ", nativeQuery = true)
    List<Question> findRandomQuestionByCategory(String category, int numsQ);
}
