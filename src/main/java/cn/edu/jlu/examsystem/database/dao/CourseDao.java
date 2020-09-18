package cn.edu.jlu.examsystem.database.dao;

import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import cn.edu.jlu.examsystem.database.entity.CourseSummaryEntity;
import cn.edu.jlu.examsystem.database.mapper.CourseMapper;
import cn.edu.jlu.examsystem.database.mapper.RelationUserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author WangZeying 2020/9/14 19:34
 */
@Repository
public class CourseDao {


    private final CourseMapper courseMapper;
    private final RelationUserMapper relationUserMapper;

    public CourseDao(CourseMapper courseMapper, RelationUserMapper relationUserMapper) {
        this.courseMapper = courseMapper;
        this.relationUserMapper = relationUserMapper;
    }

    public Long createCourse(CourseEntity entity, List<Long> teacherIds) {
        courseMapper.insert(entity);
        relationUserMapper.batchInsertTeacherByIds(entity.getId(), teacherIds);
        return entity.getId();
    }

    public CourseEntity findDetailById(Long id) {
        return courseMapper.selectDetailById(id);
    }

    public PageInfo<CourseSummaryEntity> query(CourseMapper.CourseQuery query) {
        PageHelper.startPage(query.getPageNum(), query.getPerPage(),"id desc");
        PageInfo<Long> page = PageInfo.of(courseMapper.queryForIds(query));
        List<Long> ids = page.getList();
        List<CourseSummaryEntity> entities;
        if (!CollectionUtils.isEmpty(ids)) {
            PageHelper.orderBy("id desc");
            entities = courseMapper.findAllByIdIn(ids);
        } else {
            entities = Collections.emptyList();
        }
        PageInfo<CourseSummaryEntity> pageInfo = new PageInfo<>(entities);
        pageInfo.setPageNum(query.getPageNum());
        pageInfo.setPageSize(query.getPerPage());
        pageInfo.setTotal(page.getTotal());
        pageInfo.setPages(page.getPages());
        return pageInfo;
    }

    public Boolean isOwner(Long courseId, Long userId) {
        return courseMapper.isOwner(courseId, userId);
    }

    public Boolean delete(Long id) {
        return courseMapper.softDelete(id);
    }

    public int insertStudentById(Long courseId, Long userId) {
        return relationUserMapper.insertStudentById(courseId, userId);
    }

    public int deleteStudentById(Long courseId, Long userId) {
        return relationUserMapper.deleteStudentByCourseId(courseId, userId);
    }
}
