package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;
import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/18 14:33
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamRecord {
    
    private Long examPlanId;
    
    private Long paperId;
    
    private Integer factScore;
    
    @JsonRawValue
    private String content;
    
    @JsonRawValue
    private String factScoreDetail;
    
    /**
     * '0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;
     */
    private Byte releaseStatusId;
    
    private LocalDateTime createdTime;
    
    private LocalDateTime updatedTime;
    
    @Nullable
    public static ExamRecord from(ExamRecordEntity entity) {
        if (entity == null) {
            return null;
        }
        ExamRecord record = new ExamRecord();
        BeanUtils.copyProperties(entity, record);
        return record;
    }
}
