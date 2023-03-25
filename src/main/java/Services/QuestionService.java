package Services;

import DTOs.QuestionDTO;
import DTOs.QuestionItemDTO;
import Interfaces.IQuestionService;
import models.Question;
import models.QuestionItem;
import models.Role;
import org.hibernate.Session;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class QuestionService implements IQuestionService {

    @Override
    public void addQuestion(QuestionDTO question) {
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Question realQuestion = new Question();

        realQuestion.setId(question.getId());
        realQuestion.setName(question.getName());

        List<QuestionItem> questionItems = new ArrayList<QuestionItem>();

        for(var q:question.getQuestionItems()){
            var questionItem = new QuestionItem();

            questionItem.setId(q.getId());
            questionItem.setTrue(q.isTrue());
            questionItem.setText(q.getText());
            questionItem.setQuestion(realQuestion);

            //context.save(questionItem);

            questionItems.add(questionItem);
        }

        realQuestion.setQuestionItems(questionItems);

        context.save(realQuestion);

        context.close();
    }

    @Override
    public void editQuestion(QuestionDTO questionDTO) {
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Question question = context.get(Question.class,questionDTO.getId());
        question.setName(questionDTO.getName());

        List<QuestionItem> newQuestionItems = new ArrayList<>();

        for(var questionItemDTO:questionDTO.getQuestionItems()){
            var questionItem = new QuestionItem();

            questionItem.setId(questionItemDTO.getId());
            questionItem.setText(questionItemDTO.getText());
            questionItem.setTrue(questionItemDTO.isTrue());

            questionItem.setQuestion(question);

            newQuestionItems.add(questionItem);
        }

        question.setQuestionItems(newQuestionItems);

        context.merge(question);

        context.close();
    }

    @Override
    public void removeQuestion(int id) {
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Question persistentInstance = context.load(Question.class, id);
        if (persistentInstance != null) {
            context.delete(persistentInstance);
        }
    }

    @Override
    public List<QuestionDTO> getAllQuestions() {
        Session context = HibernateSessionFactoryUtil.getSessionFactory().openSession();

        Query query = context.createQuery("FROM Question");
        List<Question> questions = query.list();


        List<QuestionDTO> questionDTOs=new ArrayList<QuestionDTO>();

        for(var question:questions){
            QuestionDTO qDTo = new QuestionDTO();

            qDTo.setId(question.getId());
            qDTo.setName(question.getName());

            //Query query2 = context.createQuery("FROM QuestionItem qi "+"INNER JOIN qi.question q where q.id=:id");
            //query2.setLong("id",question.getId());
            //List<QuestionItem> questionItems = query.list();

            List<QuestionItemDTO> questionItemsDTOs = new ArrayList<QuestionItemDTO>();
            for(var qItem:question.getQuestionItems()){
                var questionItemDTO=new QuestionItemDTO();

                questionItemDTO.setId(qItem.getId());
                questionItemDTO.setText(qItem.getText());
                questionItemDTO.setTrue(qItem.isTrue());

                questionItemsDTOs.add(questionItemDTO);
            }

            qDTo.setQuestionItems(questionItemsDTOs);

            questionDTOs.add(qDTo);
        }

        context.close();

        return questionDTOs;
    }


}
