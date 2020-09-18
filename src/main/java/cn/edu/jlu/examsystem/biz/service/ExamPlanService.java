package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.request.ExamPlanCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamPlan;
import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamPlan.SimpleExamRecord;
import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamPlanDetail;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.ExamPlanEntity;
import cn.edu.jlu.examsystem.database.mapper.ExamPlanMapper;
import cn.edu.jlu.examsystem.database.mapper.ExamRecordMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/17 21:47
 */
@Service
public class ExamPlanService {

    private final ExamPlanMapper examPlanMapper;

    private final ExamRecordMapper examRecordMapper;

    public ExamPlanService(ExamPlanMapper examPlanMapper, ExamRecordMapper examRecordMapper) {
        this.examPlanMapper = examPlanMapper;
        this.examRecordMapper = examRecordMapper;
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Long create(ExamPlanCreateRequest request, CoreUserInfo userInfo) {
        ExamPlanEntity entity = new ExamPlanEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setTeacherId(userInfo.getId());
        examPlanMapper.insert(entity);
        return entity.getId();
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public ExamPlanDetail selectByPrimaryKey(Long id) {
        ExamPlanEntity entity = examPlanMapper.selectByPrimaryKey(id);
        return ExamPlanDetail.fromEntity(entity);
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteById(Long id, CoreUserInfo userInfo) {
        //TODO 鉴权
        return examPlanMapper.deleteByPrimaryKey(id);
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public int updateByPrimaryKeySelective(ExamPlanEntity record) {
        return examPlanMapper.updateByPrimaryKeySelective(record);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public List<ExamPlan> findByStudentId(Long id) {
        return ConvertUtils.extractList(
                examPlanMapper.selectAllByStudentIdOrderByStartTimeDesc(id),
                ExamPlan::fromEntity);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public List<ExamPlan> findByTeacherId(Long id) {
        return ConvertUtils.extractList(
                examPlanMapper.selectAllByTeacherTeachOrderByStartTimeDesc(id),
                ExamPlan::fromEntity);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public List<SimpleExamRecord> findRecordByTeacherId(Long id) {
        return ConvertUtils.extractList(
                examRecordMapper.selectByTeacherId(id),
                SimpleExamRecord::fromEntity);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public List<ExamPlan> findByCourseId(Long id) {
        return ConvertUtils.extractList(
                examPlanMapper.selectAllByCourseIdOrderByStartTimeDesc(id),
                ExamPlan::fromEntity);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public List<ExamPlan> findByCourseId(Long courseId, Long studentId) {
        return ConvertUtils.extractList(
                examPlanMapper.selectAllByCourseIdAndStudentIdOrderByStartTimeDesc(courseId, studentId),
                ExamPlan::fromEntity);
    }
}
