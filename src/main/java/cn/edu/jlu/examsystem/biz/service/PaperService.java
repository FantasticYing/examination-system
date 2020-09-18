package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperPreviewRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.PaperQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.DescriptionVO;
import cn.edu.jlu.examsystem.biz.domain.paper.Paper;
import cn.edu.jlu.examsystem.biz.domain.paper.PaperPreview;
import cn.edu.jlu.examsystem.biz.domain.paper.QuestionView;
import cn.edu.jlu.examsystem.biz.enums.ReleaseStatus;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.ExamPlanEntity;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import cn.edu.jlu.examsystem.database.entity.PaperEntity;
import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import cn.edu.jlu.examsystem.database.mapper.ExamPlanMapper;
import cn.edu.jlu.examsystem.database.mapper.ExamRecordMapper;
import cn.edu.jlu.examsystem.database.mapper.PaperMapper;
import cn.edu.jlu.examsystem.database.mapper.QuestionMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/17 16:36
 */
@Service
public class PaperService {
    
    private final PaperMapper paperMapper;
    
    private final QuestionMapper questionMapper;
    
    private final QuestionService questionService;
    
    private final ExamRecordMapper examRecordMapper;

    private final ExamPlanMapper examPlanMapper;
    
    public PaperService(PaperMapper paperMapper, QuestionMapper questionMapper, QuestionService questionService, ExamRecordMapper examRecordMapper, ExamPlanMapper examPlanMapper) {
        this.paperMapper = paperMapper;
        this.questionMapper = questionMapper;
        this.questionService = questionService;
        this.examRecordMapper = examRecordMapper;
        this.examPlanMapper = examPlanMapper;
    }
    
    //TODO 校验
    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Long create(PaperCreateRequest request, CoreUserInfo userInfo) {
        List<@NotNull Long> ids = request.getConfigs().stream()
                .flatMap(config -> config.getQuestionIds().stream())
                .collect(Collectors.toList());
        Map<Long, DescriptionVO> idToDescription = questionService.findDescriptionByIds(ids);
        
        int totalScore = 0;
        List<QuestionView> viewList = new ArrayList<>();
        
        for (PaperCreateRequest.QuestionConfig config : request.getConfigs()) {
            List<DescriptionVO> voList = ConvertUtils.extractList(config.getQuestionIds(), idToDescription::get);
            
            totalScore += config.getScore() * voList.size();
            QuestionView view = QuestionView.builder()
                    .descriptions(voList)
                    .typeId(config.getTypeId())
                    .score(config.getScore())
                    .build();
            
            viewList.add(view);
        }
        PaperEntity entity = PaperEntity.builder()
                .subjectId(request.getSubjectId())
                .duration(request.getDuration())
                .title(request.getTitle())
                .teacherId(userInfo.getId())
                .score(totalScore)
                .contextJson(JsonUtils.toJson(viewList))
                .build();
        
        paperMapper.insert(entity);
        return entity.getId();
    }
    
    //TODO 校验
    @TargetDataSource(EXAM_READ_ONLY)
    public PaperPreview preview(PaperPreviewRequest request) {
        List<PaperPreviewRequest.PreViewConfig> configs = request.getConfigs();
        @NotNull Long subjectId = request.getSubjectId();
        int totalScore = 0;
        List<QuestionView> viewList = new ArrayList<>();
        for (PaperPreviewRequest.PreViewConfig config : configs) {
            @NotNull Short typeId = config.getTypeId();
            @NotNull Integer count = config.getCount();
            @NotNull Integer score = config.getScore();
            List<QuestionEntity> entities = questionMapper.random(
                    QuestionMapper
                            .QueryCondition.builder()
                            .subjectId(subjectId)
                            .typeId(typeId)
                            .limit(count.longValue())
                            .build());
            List<DescriptionVO> voList = ConvertUtils.extractList(entities, DescriptionVO::fromEntity);
            // 由于题目数量可能不够，所以用实际问题数算总分
            totalScore += score * voList.size();
            QuestionView view = QuestionView.builder()
                    .descriptions(voList)
                    .typeId(typeId)
                    .score(score)
                    .build();
            viewList.add(view);
        }
        return PaperPreview.builder()
                .score(totalScore)
                .questions(viewList)
                .build();
    }
    
    @TargetDataSource(EXAM_READ_ONLY)
    public Paper getById(Long id) {
        PaperEntity entity = paperMapper.selectByPrimaryKey(id);
        return Paper.fromEntity(entity);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @TargetDataSource(EXAM_READ_WRITE)
    public Paper getAndStart(Long planId, CoreUserInfo userInfo) {
        ExamRecordEntity recordEntity = examRecordMapper.selectByExamPlanIdAndStudentId(planId, userInfo.getId());
        if (recordEntity == null) {
            ExamPlanEntity examPlanEntity = examPlanMapper.selectByPrimaryKey(planId);
            if (examPlanEntity == null){
                throw new BaseException(HttpStatus.BAD_REQUEST.value(), "不存在的考试计划,可能已被删除");
            }
            recordEntity = ExamRecordEntity.builder()
                    .examPlanId(planId)
                    .studentId(userInfo.getId())
                    .studentName(userInfo.getNickname())
                    .releaseStatusId(ReleaseStatus.STARTING.getId())
                    .paperId(examPlanEntity.getPaperId())
                    .build();
            examRecordMapper.insertIfNotExist(recordEntity);
        }

        PaperEntity entity = paperMapper.selectByPrimaryKey(recordEntity.getPaperId());
        entity.setExamRecordEntity(recordEntity);
        return Paper.fromEntity(entity);
    }
    
    @Transactional(rollbackFor = Exception.class)
    @TargetDataSource(EXAM_READ_WRITE)
    public Paper getForJudge(Long planId, Long studentId) {
        ExamRecordEntity recordEntity = examRecordMapper.selectByExamPlanIdAndStudentId(planId, studentId);
        
        PaperEntity entity = paperMapper.selectByPrimaryKey(planId);
        entity.setExamRecordEntity(recordEntity);
        return Paper.fromEntity(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    @TargetDataSource(EXAM_READ_ONLY)
    public List<Paper> query(PaperQueryRequest request, CoreUserInfo userInfo) {
        PaperMapper.QueryCondition condition = PaperMapper.QueryCondition.builder()
                .subjectId(request.getSubjectId())
                .teacherId(request.isMyCreated() ? userInfo.getId() : null)
                .keyword(request.getKeyword())
                .build();
        List<PaperEntity> entities = paperMapper.query(condition);
        return Paper.fromEntity(entities);
    }
    
}
