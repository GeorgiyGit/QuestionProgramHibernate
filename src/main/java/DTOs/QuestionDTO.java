package DTOs;

import lombok.Data;
import models.QuestionItem;

import java.util.ArrayList;
import java.util.List;

@Data
public class QuestionDTO {
    private int id;
    private String name;
    private List<QuestionItemDTO> questionItems=new ArrayList<QuestionItemDTO>();
}
