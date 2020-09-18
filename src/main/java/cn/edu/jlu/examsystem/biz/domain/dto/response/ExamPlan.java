package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.database.entity.ExamPlanEntity;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

/**
 * @author WangZeying 2020/9/18 9:28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamPlan {
    private Long id;

    private String subjectName;

    private Long courseId;

    private Long paperId;

    private String paperTitle;

    private Integer fullScore;

    private String teacherName;

    private SimpleExamRecord examRecord;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime startTime;

    @ApiModelProperty(example = "1601395200")
    private LocalDateTime endTime;

    public static ExamPlan fromEntity(ExamPlanEntity entity) {
        ExamPlan detail = new ExamPlan();
        BeanUtils.copyProperties(entity, detail);
        if (entity.getExamRecordEntity() != null) {
            detail.setExamRecord(SimpleExamRecord.fromEntity(entity.getExamRecordEntity()));
        }
        return detail;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleExamRecord {

        private Long id;

        private Long paperId;

        private Long examPlanId;

        private Long studentId;

        private String studentName;

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

        private String title;
        public static SimpleExamRecord fromEntity(ExamRecordEntity entity) {
            SimpleExamRecord record = new SimpleExamRecord();
            BeanUtils.copyProperties(entity, record);
            record.setContent(entity.getContentJson());
            record.setFactScoreDetail(entity.getFactScoreDetailJson());
            return record;
        }
    }

}
