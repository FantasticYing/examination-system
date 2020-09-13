package cn.edu.jlu.examsystem.biz.domain.question;

import cn.edu.jlu.examsystem.biz.domain.question.answer.Answer;
import cn.edu.jlu.examsystem.biz.domain.question.description.Description;
import cn.edu.jlu.examsystem.biz.enums.QuestionTypeEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.swagger.annotations.ApiModel;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "typeId",visible = true)
@JsonSubTypes({
        @JsonSubTypes.Type(value = QuestionOfChoice.class, name = "1"),
        @JsonSubTypes.Type(value = QuestionOfChoiceWithMultiAnswer.class, name = "2"),
        @JsonSubTypes.Type(value = QuestionOfTrueOrFalse.class, name = "3"),
        @JsonSubTypes.Type(value = QuestionOfShortAnswer.class, name = "5")
})
@ApiModel(subTypes = {
        QuestionOfChoice.class,
        QuestionOfChoiceWithMultiAnswer.class,
        QuestionOfTrueOrFalse.class,
        QuestionOfShortAnswer.class
})
public abstract class Question<D extends Description, A extends Answer> {

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
    @ApiModelProperty(dataType = "cn.edu.jlu.examsystem.biz.domain.question.description.DescriptionOfChoice")
    private D description;
    @NotNull
    @Valid
    @ApiModelProperty(dataType = "cn.edu.jlu.examsystem.biz.domain.question.answer.AnswerOfSingleOption")
    private A answer;

    @ApiModelProperty(hidden = true)
    @JsonIgnore
    public QuestionTypeEnum getTypeEnum() {
        return QuestionTypeEnum.fromId(typeId);
    }

}
