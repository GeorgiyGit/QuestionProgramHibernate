package Program;

import DTOs.QuestionDTO;
import DTOs.QuestionItemDTO;
import Services.QuestionService;
import Services.RoleService;
import models.Question;
import models.QuestionItem;
import models.Role;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import utils.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static QuestionService questionService=new QuestionService();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random r = new Random();

        while(true) {
            System.out.println("Enter user role:");
            System.out.println("[1]. Moderator");
            System.out.println("[2]. Customer");


            int n = Integer.parseInt(input.next());
            switch (n) {
                case 1:
                    int n2 = 1;
                    while (n2 != 5) {
                        System.out.println("Enter action:");
                        System.out.println("[1]. Add question");
                        System.out.println("[2]. Update question");
                        System.out.println("[3]. Remove question");
                        System.out.println("[4]. Get all questions");
                        System.out.println("[5]. Exit");

                        n2 = Integer.parseInt(input.next());

                        switch (n2) {
                            case 1:
                                QuestionDTO questionDTO = new QuestionDTO();

                                System.out.println("Enter question text:");
                                questionDTO.setName(input.next());


                                List<QuestionItemDTO> questionItemDTOs = new ArrayList<QuestionItemDTO>();

                                boolean isNext = true;
                                while (isNext) {
                                    QuestionItemDTO questionItemDTO = new QuestionItemDTO();

                                    System.out.println("Enter question option text:");
                                    questionItemDTO.setText(input.next());


                                    System.out.println("Is it true? (true, false)");
                                    questionItemDTO.setTrue(Boolean.parseBoolean(input.next()));

                                    questionItemDTOs.add(questionItemDTO);

                                    if (questionItemDTOs.size() >= 2) {
                                        System.out.println("Continue? (true, false)");
                                        isNext = Boolean.parseBoolean(input.next());

                                    }
                                    System.out.println();
                                }

                                questionDTO.setQuestionItems(questionItemDTOs);

                                questionService.addQuestion(questionDTO);
                                break;
                            case 2:
                                List<QuestionDTO> questionDTOs = questionService.getAllQuestions();

                                printQuestions(questionDTOs);

                                System.out.println("Enter id:");
                                int id = Integer.parseInt(input.next());


                                QuestionDTO question = questionDTOs.stream().filter(q -> q.getId() == id).findAny().orElse(null);
                                if (question != null) {
                                    System.out.println(question.getId() + " | " + question.getName() + " | ");

                                    printQuestionItems(question);

                                    int n4 = 1;
                                    while (n4 != 5) {


                                        System.out.println("[1]. Edit question");
                                        System.out.println("[2]. Add new option");
                                        System.out.println("[3]. Remove option");
                                        System.out.println("[4]. Edit option");
                                        System.out.println("[5]. Save and exit");

                                        n4 = Integer.parseInt(input.next());

                                        switch (n4) {
                                            case 1:
                                                System.out.println("Enter new question");
                                                question.setName(input.next());
                                                break;
                                            case 2:
                                                printQuestionItems(question);
                                                QuestionItemDTO newOption = new QuestionItemDTO();

                                                System.out.println("Enter text");
                                                newOption.setText(input.next());

                                                System.out.println("Is it true? (true, false)");
                                                newOption.setTrue(Boolean.parseBoolean(input.next()));

                                                question.getQuestionItems().add(newOption);
                                                break;
                                            case 3:
                                                printQuestionItems(question);

                                                System.out.println("Enter id");
                                                int id2 = Integer.parseInt(input.next());

                                                QuestionItemDTO removedQuestion = question.getQuestionItems().stream().filter(qi -> qi.getId() == id2).findAny().orElse(null);

                                                if (removedQuestion != null)
                                                    question.getQuestionItems().remove(removedQuestion);
                                                break;
                                            case 4:
                                                printQuestionItems(question);

                                                System.out.println("Enter id");
                                                int id3 = Integer.parseInt(input.next());

                                                QuestionItemDTO selectedQuestion = question.getQuestionItems().stream().filter(qi -> qi.getId() == id3).findAny().orElse(null);

                                                if (selectedQuestion != null) {
                                                    System.out.println("Enter new text");
                                                    selectedQuestion.setText(input.next());

                                                    System.out.println("Is it true? (true, false)");
                                                    selectedQuestion.setTrue(Boolean.parseBoolean(input.next()));
                                                }
                                                break;
                                            case 5:
                                                questionService.editQuestion(question);
                                                break;
                                        }
                                    }

                                }
                                break;
                            case 3:
                                List<QuestionDTO> questionDTOs2 = questionService.getAllQuestions();

                                printQuestions(questionDTOs2);

                                System.out.println("Enter id:");
                                int id4 = Integer.parseInt(input.next());

                                questionService.removeQuestion(id4);
                                break;
                            case 4:
                                List<QuestionDTO> questionDTOs3 = questionService.getAllQuestions();

                                printQuestions(questionDTOs3);
                                break;
                            case 5:

                                break;
                        }
                    }
                    break;
                case 2:
                    int points=0;
                    int count=0;

                    List<QuestionDTO> questions = questionService.getAllQuestions();

                    while(questions.size()>0){
                        System.out.println("Your points: "+points);

                        System.out.println("Question № "+count+1);

                        int randN = r.nextInt(questions.size());

                        QuestionDTO q = questions.get(randN);

                        System.out.println(q.getName());

                        for(int i=0;i<q.getQuestionItems().size();i++){
                            var qItem = q.getQuestionItems().get(i);
                            int n6= i+Integer.parseInt("1");
                            System.out.println("["+n6+"]. "+qItem.getText());
                        }

                        System.out.println("Enter correct option");
                        int cOption = Integer.parseInt(input.next());

                        if(q.getQuestionItems().get(cOption-1).isTrue()==true){
                            System.out.println("YOU ARE CORRECT!!!");
                            points++;
                        }
                        else{
                            System.out.println("It`s incorrect ):");
                        }
                        count++;

                        System.out.println("Continue? (true, false)");
                        boolean isContinue=Boolean.parseBoolean(input.next());

                        if(!isContinue){
                            break;
                        }
                    }
                    float mark = points*12/count;
                    System.out.println("Your total points: "+points);
                    System.out.println("Questions count: "+count);
                    System.out.println("Your mark is: "+mark+"/12");
                    input.next();
                    break;
            }
        }
    }

    private static void printQuestions(List<QuestionDTO> questionDTOs){
        for(var questionDTO:questionDTOs){
            System.out.println(questionDTO.getId()+" | "+questionDTO.getName());
        }
    }

    private static void printQuestionItems(QuestionDTO question){
        System.out.println("Options: ");
        for(var questionItem:question.getQuestionItems()){
            System.out.println(questionItem.getId()+" | "+questionItem.getText()+" | "+questionItem.isTrue());
        }
    }


    private static void insertRole(RoleService roleService){
        Scanner input = new Scanner(System.in);
        System.out.println("Enter role");
        String name = input.next();
        Role role = new Role();
        role.setName(name);
        roleService.insertRole(role);
    }
    private static void showRoles(RoleService roleService){
        List<Role> roles = roleService.showRoles();
        for(int i=0;i<roles.size();i++){
            System.out.println(roles.get(i));
        }
    }
}
