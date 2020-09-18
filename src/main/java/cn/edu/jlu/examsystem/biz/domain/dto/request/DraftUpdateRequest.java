package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.question.answer.Answer;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author WangZeying 2020/9/18 15:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftUpdateRequest {

    @NotNull
    private Long examPlanId;

    @NotNull
    private Map<Long, @NotNull @Valid Answer> idToAnswer;

    public ExamRecordEntity toEntity(CoreUserInfo userInfo) {
        return ExamRecordEntity.builder()
                .examPlanId(examPlanId)
                .releaseStatusId((byte) 1)
                .studentId(userInfo.getId())
                .studentName(userInfo.getNickname())
                .contentJson(JsonUtils.toJson(idToAnswer))
                .build();
    }

}
