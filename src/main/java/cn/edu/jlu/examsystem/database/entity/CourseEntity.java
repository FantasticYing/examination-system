package cn.edu.jlu.examsystem.database.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author WangZeying 2020/9/13 18:39
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    private Long id;

    private Long subjectId;

    private String subjectName;

    private String subtitle;

    private Long creatorId;

    private String creatorName;

    @Transient
    private List<RelationUserEntity> teachers;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;
}