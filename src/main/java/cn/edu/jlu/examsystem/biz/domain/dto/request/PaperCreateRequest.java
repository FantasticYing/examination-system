package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author WangZeying 2020/9/17 15:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperCreateRequest {
    @NotNull
    private Long subjectId;
    @NotNull
    @Size(min = 10)
    private String title;

    @Min(1)
    private Integer duration;

    @NotNull
    private List<@Valid QuestionConfig> configs;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionConfig{
        @NotNull
        private Short typeId;
        @NotNull
        private Integer score;
        @NotNull
        private List<@NotNull Long> questionIds;
    }
}
