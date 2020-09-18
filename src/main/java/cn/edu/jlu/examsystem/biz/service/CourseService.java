package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.Pagination;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.CourseQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseDetail;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CourseSummary;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.dao.CourseDao;
import cn.edu.jlu.examsystem.database.entity.CourseEntity;
import cn.edu.jlu.examsystem.database.entity.CourseSummaryEntity;
import cn.edu.jlu.examsystem.database.mapper.CourseMapper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/13 18:39
 */
@Service
public class CourseService {

    private final CourseDao courseDao;

    public CourseService(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Long createCourse(CourseCreateRequest request, CoreUserInfo userInfo) {
        CourseEntity entity = request.toEntity(userInfo);
        Set<Long> ids = new LinkedHashSet<>(request.getTeacherIds().size() + 1);
        ids.add(userInfo.getId());
        ids.addAll(request.getTeacherIds());
        return courseDao.createCourse(entity, new ArrayList<>(ids));
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public BatchGet<CourseSummary> query(CourseQueryRequest request, CoreUserInfo userInfo) {
        CourseMapper.CourseQuery query = new CourseMapper.CourseQuery();
        BeanUtils.copyProperties(request, query);
        if (request.isMyTaught()) {
            query.setTeacherId(userInfo.getId());
        }
        if (request.isMyCreated()) {
            query.setCreatorId(userInfo.getId());
        }
        if (request.isMyChosen()) {
            query.setStudentId(userInfo.getId());
        }
        PageInfo<CourseSummaryEntity> page = courseDao.query(query);
        return BatchGet.of(CourseSummary.fromEntities(page.getList()), Pagination.fromPage(page));
    }

    public CourseDetail getDetailById(Long id) {
        return CourseDetail.fromEntity(courseDao.findDetailById(id));
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteCourse(Long id, CoreUserInfo userInfo) {
        if (!courseDao.isOwner(id, userInfo.getId())) {
            throw new BaseException(HttpStatus.FORBIDDEN.value(), "只有创建者有权限删除");
        }
        return courseDao.delete(id);
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public int chooseCourse(Long id, CoreUserInfo userInfo) {
        return courseDao.insertStudentById(id,userInfo.getId());
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public int dropStudentCourse(Long id, CoreUserInfo userInfo) {
        return courseDao.deleteStudentById(id,userInfo.getId());
    }

}
