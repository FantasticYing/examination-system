package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.database.entity.ExamPlanEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/18 9:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamPlanDetail {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private Long courseId;

    private Long paperId;

    private String paperTitle;

    private Integer fullScore;

    private Long teacherId;

    private String teacherName;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime startTime;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime endTime;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime createdTime;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime updatedTime;

    public static ExamPlanDetail fromEntity(ExamPlanEntity entity) {
        ExamPlanDetail detail = new ExamPlanDetail();
        BeanUtils.copyProperties(entity, detail);
        return detail;
    }
}
