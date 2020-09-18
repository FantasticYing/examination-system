package cn.edu.jlu.examsystem.biz.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author WangZeying 2020/9/17 17:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperPreviewRequest {

    @NotNull
    private Long subjectId;
    @NotNull
    private List<@Valid PreViewConfig> configs;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PreViewConfig {
        @NotNull
        private Short typeId;
        @NotNull
        private Integer score;
        @NotNull
        private Integer count;
    }
}
