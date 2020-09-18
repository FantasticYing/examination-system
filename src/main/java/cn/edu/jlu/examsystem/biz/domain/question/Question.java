package cn.edu.jlu.examsystem.biz.domain.question;

import cn.edu.jlu.examsystem.biz.domain.question.answer.Answer;
import cn.edu.jlu.examsystem.biz.domain.question.description.Description;
import cn.edu.jlu.examsystem.biz.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author WangZeying 2020/9/7 23:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question{

    /**
     * 0-未分类;1-单选;2-多选;3-判断;5:简答
     */
    @NotNull
    @Min(1)
    @Max(5)
    @ApiModelProperty(notes = "1-单选;2-多选;3-判断;5:简答")
    private Short typeId;
    @Valid
    @NotNull
    private Description description;
    @NotNull
    @Valid
    private Answer answer;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public QuestionTypeEnum getTypeEnum() {
        return QuestionTypeEnum.fromId(typeId);
    }

}
