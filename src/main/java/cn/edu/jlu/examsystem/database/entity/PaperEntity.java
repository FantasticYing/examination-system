package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
* @author WangZeying 2020/9/17 16:36
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaperEntity {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private String title;

    private Long teacherId;

    private String teacherName;

    private Integer score;

    private Integer duration;

    private String contextJson;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
    
    private ExamRecordEntity examRecordEntity;
}