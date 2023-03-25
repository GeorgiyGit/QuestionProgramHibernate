package Interfaces;

import DTOs.QuestionDTO;

import java.util.List;

public interface IQuestionService {
    void addQuestion(QuestionDTO question);
    void editQuestion(QuestionDTO questionDTO);
    void removeQuestion(int id);
    List<QuestionDTO> getAllQuestions();
}
