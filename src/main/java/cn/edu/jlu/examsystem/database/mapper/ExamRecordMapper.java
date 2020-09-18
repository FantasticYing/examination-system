package cn.edu.jlu.examsystem.database.mapper;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
* @author WangZeying 2020/9/18 13:54
*/
@Mapper
public interface ExamRecordMapper {
    int insertIfNotExist(ExamRecordEntity record);

    ExamRecordEntity selectByPrimaryKey(Long id);

    int updateByExamPlanIdAndStudentIdSelective(ExamRecordEntity record);

    ExamRecordEntity selectByExamPlanIdAndStudentId(@Param("examPlanId")Long examPlanId,@Param("studentId")Long studentId);

    List<ExamRecordEntity> selectByTeacherId(@Param("teacherId")Long studentId);

    Map<String,Integer> selectStats(@Param("planId")Long planId);

}