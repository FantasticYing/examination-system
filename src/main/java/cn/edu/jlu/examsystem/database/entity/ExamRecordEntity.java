package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
* @author WangZeying 2020/9/18 13:54
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordEntity {
    private Long id;

    private Long examPlanId;

    private Long paperId;

    private Long studentId;

    private String studentName;

    private Integer factScore;

    private String contentJson;
    
    private String factScoreDetailJson;

    private String title;

    /**
    * '0-未答卷，1-已开考，开始计时，2-已交卷，等待判卷，4-完成;
    */
    private Byte releaseStatusId;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}