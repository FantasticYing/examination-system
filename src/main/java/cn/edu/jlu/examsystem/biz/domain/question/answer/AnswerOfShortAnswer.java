package cn.edu.jlu.examsystem.biz.domain.question.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @author WangZeying 2020/9/8 17:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnswerOfShortAnswer extends Answer{

    @NotBlank
    String answer;

    @Override
    public String  getAnswer() {
        return answer;
    }
}
