package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangZeying 2020/9/16 16:31
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseSummaryEntity {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private String subtitle;

    private String creatorName;

    private List<String> teacherNames;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}
