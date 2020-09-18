package cn.edu.jlu.examsystem.biz.domain.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/18 9:07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamPlanCreateRequest {

    @NotNull
    private Long courseId;

    @NotNull
    private Long paperId;

    @NotNull
    @ApiModelProperty(example = "1601395200")
    private LocalDateTime startTime;

    @NotNull
    @ApiModelProperty(example = "1601395200")
    private LocalDateTime endTime;
}
