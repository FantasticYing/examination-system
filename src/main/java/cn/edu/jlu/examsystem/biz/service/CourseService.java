package cn.edu.jlu.examsystem.biz.service;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import cn.edu.jlu.examsystem.database.mapper.CourseMapper;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
/**
* @author WangZeying 2020/9/13 18:39
*/
@Service
public class CourseService{

    @Resource
    private CourseMapper courseMapper;

    
    public int deleteByPrimaryKey(Long id) {
        return courseMapper.deleteByPrimaryKey(id);
    }

    
    public int insert(CourseEntity record) {
        return courseMapper.insert(record);
    }

    
    public int insertSelective(CourseEntity record) {
        return courseMapper.insertSelective(record);
    }

    
    public CourseEntity selectByPrimaryKey(Long id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    
    public int updateByPrimaryKeySelective(CourseEntity record) {
        return courseMapper.updateByPrimaryKeySelective(record);
    }

    
    public int updateByPrimaryKey(CourseEntity record) {
        return courseMapper.updateByPrimaryKey(record);
    }

}
