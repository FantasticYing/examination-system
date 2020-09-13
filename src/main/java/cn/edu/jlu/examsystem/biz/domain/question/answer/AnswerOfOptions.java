package cn.edu.jlu.examsystem.biz.domain.question.answer;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author WangZeying 2020/9/8 17:05
 */
public class AnswerOfOptions extends Answer{

    @Valid
    @Size(min = 1, message = "至少一个正确选项")
    private List<@NotNull Short> optionIds;

    @Override
    public List<Short> getAnswer() {
        return optionIds;
    }
}
