package DTOs;

import lombok.Data;

@Data
public class QuestionItemDTO {
    private int id;
    private String text;
    private boolean isTrue;
}
