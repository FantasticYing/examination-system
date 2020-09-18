package cn.edu.jlu.examsystem.database.mapper;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import cn.edu.jlu.examsystem.database.entity.CourseSummaryEntity;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author WangZeying 2020/9/13 18:39
 */
@Mapper
public interface CourseMapper {

    Boolean softDelete(Long id);

    int insert(CourseEntity record);

    int insertSelective(CourseEntity record);

    CourseSummaryEntity selectSummaryById(Long id);

    Boolean isOwner(@Param("courseId") Long courseId, @Param("userId")Long userId);

    CourseEntity selectDetailById(Long id);

    List<Long> queryForIds(@Param("query") CourseQuery query);

    List<CourseSummaryEntity> findAllByIdIn(@Param("idCollection")Collection<Long> idCollection);

    @Deprecated
    List<CourseSummaryEntity> query(@Param("query") CourseQuery query);

    int updateByPrimaryKeySelective(CourseEntity record);

    int updateByPrimaryKey(CourseEntity record);

    @Data
    @NoArgsConstructor
    class CourseQuery {
        private Long subjectId;
        private Long creatorId;
        private Long teacherId;
        private Long studentId;
        private int perPage;
        private int pageNum;
    }
}