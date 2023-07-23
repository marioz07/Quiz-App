package com.Atharva.QuizApp.service;

import com.Atharva.QuizApp.dao.QuestionDao;
import com.Atharva.QuizApp.dao.QuizDao;
import com.Atharva.QuizApp.model.Question;
import com.Atharva.QuizApp.model.QuestionWrapper;
import com.Atharva.QuizApp.model.Quiz;
import com.Atharva.QuizApp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizSevice {
    @Autowired
    QuizDao quizdao;

    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numsQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionByCategory(category, numsQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestion(questions);
        quizdao.save(quiz);
        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizdao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestion();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for (Question q: questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(),q.getQuestionTitle(),q.getOption1(),q.getOption2(),q.getOption3(),q.getOption4());
            questionsForUser.add(qw);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizdao.findById(id).get();
        List<Question> questions = quiz.getQuestion();
        int right =0;
        int i =0;
        for(Response response: responses){
            if(response.getResponse().equals(questions.get(i).getRightAnswer()))
                right++;
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }
}
