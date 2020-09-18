package cn.edu.jlu.examsystem.biz.domain.paper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author WangZeying 2020/9/19 19:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamResult {
    
    private Long examPlanId;
    
    private Long paperId;
    
    private Integer factScore;
    
    private String content;
    
    private String factScoreDetail;
    
    /**
     * '0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;
     */
    private Byte releaseStatusId;
    
}
