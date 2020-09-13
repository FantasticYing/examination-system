package cn.edu.jlu.examsystem.biz.domain.question.answer;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * @author WangZeying 2020/9/8 0:18
 */
@Data
@Validated
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class Answer {

    public abstract Object getAnswer();

}
