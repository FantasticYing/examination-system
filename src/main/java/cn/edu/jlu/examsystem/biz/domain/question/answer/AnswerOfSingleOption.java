package cn.edu.jlu.examsystem.biz.domain.question.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author WangZeying 2020/9/8 0:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnswerOfSingleOption extends Answer {

    @NotNull
    private Short optionId;

    @Override
    public Short getAnswer() {
        return optionId;
    }
}
