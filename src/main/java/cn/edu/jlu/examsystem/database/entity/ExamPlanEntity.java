package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
* @author WangZeying 2020/9/17 21:47
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamPlanEntity {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private Long courseId;

    private Long paperId;

    private String paperTitle;

    private Integer fullScore;

    private Long teacherId;

    private String teacherName;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private ExamRecordEntity examRecordEntity;
}