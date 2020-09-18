package cn.edu.jlu.examsystem.database.mapper;
import cn.edu.jlu.examsystem.database.entity.ExamPlanEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author WangZeying 2020/9/17 21:47
*/
@Mapper
public interface ExamPlanMapper {
    Boolean deleteByPrimaryKey(Long id);

    int insert(ExamPlanEntity record);

    ExamPlanEntity selectByPrimaryKey(Long id);

    List<ExamPlanEntity> selectAllByStudentIdOrderByStartTimeDesc(@Param("studentId")Long studentId);

    List<ExamPlanEntity> selectAllByTeacherTeachOrderByStartTimeDesc(@Param("teacherId")Long teacherId);

    List<ExamPlanEntity> selectAllByCourseIdOrderByStartTimeDesc(@Param("courseId")Long courseId);

    List<ExamPlanEntity> selectAllByCourseIdAndStudentIdOrderByStartTimeDesc(
            @Param("courseId")Long courseId,
            @Param("studentId")Long studentId);

    int updateByPrimaryKeySelective(ExamPlanEntity record);
}