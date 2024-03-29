package cn.edu.jlu.examsystem.biz.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/10 22:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionVO {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private Long creatorId;

    private String creatorName;

    /**
     * 0-未分类;1-单选;2-多选;3-判断;4-简答
     */
    private Short typeId;

    @JsonRawValue
    @ApiModelProperty(dataType="cn.edu.jlu.examsystem.biz.domain.question.description.Description")
    private String description;

    @JsonRawValue
    @ApiModelProperty(dataType="cn.edu.jlu.examsystem.biz.domain.question.answer.Answer")
    private String answer;
}
