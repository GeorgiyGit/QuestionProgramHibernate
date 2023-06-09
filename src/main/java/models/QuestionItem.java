package models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="tbl_question_item")
public class QuestionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 500, nullable = false)
    private String text;
    private boolean isTrue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="question_id", nullable = false)
    private Question question;
}
