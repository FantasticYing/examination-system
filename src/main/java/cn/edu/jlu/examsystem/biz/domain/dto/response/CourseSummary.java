package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.database.entity.CourseSummaryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangZeying 2020/9/14 22:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseSummary {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private String subtitle;

    private List<String> teacherNames;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public static CourseSummary fromEntity(CourseSummaryEntity entity) {
        CourseSummary dto = new CourseSummary();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    public static List<CourseSummary> fromEntities(List<CourseSummaryEntity> entities) {
        return ConvertUtils.extractList(entities, CourseSummary::fromEntity);
    }
}
