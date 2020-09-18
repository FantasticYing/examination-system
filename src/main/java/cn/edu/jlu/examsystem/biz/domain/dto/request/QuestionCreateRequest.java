package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.question.Question;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author WangZeying 2020/9/7 23:08
 */
@Data
@NoArgsConstructor
public class QuestionCreateRequest {

    @NotNull
    private Long subjectId;

    @ApiModelProperty(value = "回传科目名称", notes = "可选，不填则会补全",required = false)
    private String subjectName;

    @NotNull
    @Valid
    private Question question;

}
