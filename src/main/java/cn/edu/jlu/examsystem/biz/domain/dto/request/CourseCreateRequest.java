package cn.edu.jlu.examsystem.biz.domain.dto.request;

import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author WangZeying 2020/9/14 18:15
 */
@Data
@Builder
@AllArgsConstructor
public class CourseCreateRequest {

    @NotNull
    private Long subjectId;

    @NotBlank
    private String subtitle;

    @NotNull
    private List<@Valid Long> teacherIds;

    @NotNull
    private LocalDateTime startTime;

    @NotNull
    private LocalDateTime endTime;

    public CourseEntity toEntity(CoreUserInfo userInfo) {
        CourseEntity entity = new CourseEntity();
        BeanUtils.copyProperties(this, entity);
        entity.setCreatorId(userInfo.getId());
        entity.setCreatorName(userInfo.getNickname());
        return entity;
    }

}
