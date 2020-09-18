package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/18 15:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftResponse {

    private Long examPlanId;

    @JsonRawValue
    private String answerList;

    private Integer factScore;

    /**
     * '0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;
     */
    private Byte releaseStatusId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public DraftResponse from(ExamRecordEntity entity) {
        DraftResponse draft = new DraftResponse();
        BeanUtils.copyProperties(entity, draft);
        draft.setAnswerList(entity.getContentJson());
        return draft;
    }

}
