package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author WangZeying 2020/9/25 10:50
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckRequest {
    @NotNull
    private Long examPlanId;

    @NotNull
    private Long studentId;

    private Map<Long,Integer> idToScore;

    public ExamRecordEntity toEntity(CoreUserInfo userInfo) {
        return ExamRecordEntity.builder()
                .examPlanId(examPlanId)
                .studentId(studentId)
                .releaseStatusId((byte) 4)
                .factScoreDetailJson(JsonUtils.toJson(idToScore))
                .factScore(idToScore.values().stream().mapToInt(value -> value).sum())
                .build();
    }
}
