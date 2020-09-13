package cn.edu.jlu.examsystem.biz.domain.question.answer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author WangZeying 2020/9/8 17:39
 */
@Data
@Validated
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AnswerOfTrueOrFalse extends Answer{

    @NotNull
    Boolean answer;

}
