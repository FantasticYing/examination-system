package cn.edu.jlu.examsystem.database.mapper;

import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import org.apache.ibatis.annotations.Mapper;

/**
* @author WangZeying 2020/9/13 18:39
*/
@Mapper
public interface CourseMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CourseEntity record);

    int insertSelective(CourseEntity record);

    CourseEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CourseEntity record);

    int updateByPrimaryKey(CourseEntity record);
}