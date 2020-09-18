package cn.edu.jlu.examsystem.database.mapper;

import cn.edu.jlu.examsystem.database.entity.RelationUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author WangZeying 2020/9/13 19:35
 */
@Mapper
public interface RelationUserMapper {

    int deleteByCourseId(Long id);

    RelationUserEntity selectTeachersByCourseId(Long id);

    RelationUserEntity selectStudentsByCourseId(Long id);

    @Deprecated
    int batchInsertTeachers(@Param("courseId")Long courseId, @Param("list") List<RelationUserEntity> list);

    @Deprecated
    int batchInsertStudents(@Param("courseId")Long courseId, @Param("list") List<RelationUserEntity> list);

    int batchInsertTeacherByIds(@Param("courseId")Long courseId, @Param("ids") List<Long> ids);

    int insertStudentById(@Param("courseId")Long courseId, @Param("userId") Long id);

    int deleteStudentByCourseId(@Param("courseId")Long courseId, @Param("userId")Long userId);

    int deleteTeacherByCourseId(@Param("courseId")Long courseId, @Param("userId")Long userId);

    int deleteAllTeachersByCourseId(@Param("courseId")Long courseId, @Param("userId")Long userId);

}