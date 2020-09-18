package cn.edu.jlu.examsystem.biz.domain.dto.response;

import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangZeying 2020/9/16 17:16
 */
@Data
@NoArgsConstructor
public class CourseDetail {

    private Long id;

    private Long subjectId;

    private String subjectName;

    private String subtitle;

    private Long creatorId;

    private String creatorName;

    private List<IdAndName<Long>> teachers;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public static CourseDetail fromEntity(CourseEntity entity) {
        CourseDetail dto = new CourseDetail();
        BeanUtils.copyProperties(entity, dto);
        List<IdAndName<Long>> names = ConvertUtils.extractList(
                entity.getTeachers(),
                teacher -> new IdAndName<>(teacher.getUserId(), teacher.getUserName()));
        dto.setTeachers(names);
        return dto;
    }

    public static List<CourseDetail> fromEntities(List<CourseEntity> entities) {
        return ConvertUtils.extractList(entities, CourseDetail::fromEntity);
    }

}
