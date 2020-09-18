package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.request.CheckRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.DraftUpdateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.DescriptionVO;
import cn.edu.jlu.examsystem.biz.domain.paper.QuestionView;
import cn.edu.jlu.examsystem.biz.domain.question.answer.Answer;
import cn.edu.jlu.examsystem.biz.enums.QuestionTypeEnum;
import cn.edu.jlu.examsystem.biz.enums.ReleaseStatus;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.ExamRecordEntity;
import cn.edu.jlu.examsystem.database.entity.PaperEntity;
import cn.edu.jlu.examsystem.database.mapper.ExamRecordMapper;
import cn.edu.jlu.examsystem.database.mapper.PaperMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.edu.jlu.examsystem.biz.common.Constants.QUESTION_VIEW_TYPE;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/18 15:44
 */
@Service
public class ExamService {

    private final ExamRecordMapper recordMapper;

    private final PaperMapper paperMapper;

    private final QuestionService questionService;

    public ExamService(ExamRecordMapper recordMapper, PaperMapper paperMapper, PaperService paperService, QuestionService questionService) {
        this.recordMapper = recordMapper;
        this.paperMapper = paperMapper;
        this.questionService = questionService;
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Integer saveDraft(DraftUpdateRequest request,
                             CoreUserInfo userInfo) {
        //TODO 校验
        ExamRecordEntity entity = request.toEntity(userInfo);
        entity.setReleaseStatusId(ReleaseStatus.STARTING.getId());
        return recordMapper.updateByExamPlanIdAndStudentIdSelective(entity);
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Integer submit(DraftUpdateRequest request,
                          CoreUserInfo userInfo) {
        ExamRecordEntity recordEntity = recordMapper.selectByExamPlanIdAndStudentId(request.getExamPlanId(), userInfo.getId());
        //TODO 客观题判断
        PaperEntity paper = paperMapper.selectByPrimaryKey(recordEntity.getPaperId());
        List<QuestionView> viewList = JsonUtils.fromJson(paper.getContextJson(), QUESTION_VIEW_TYPE);

        Map<Long, Integer> idToScore = new HashMap<>(viewList.size());
        List<Long> ids = new ArrayList<>(viewList.size());

        // 获取题目分数和id
        for (QuestionView questionView : viewList) {
            for (DescriptionVO question : questionView.getDescriptions()) {
                if (QuestionTypeEnum.isObjective(question.getTypeId())) {
                    idToScore.put(question.getId(), questionView.getScore());
                    Long id = question.getId();
                    ids.add(id);
                }
            }
        }

        // 获取正确答案
        Map<Long, Answer> idToRightAnswer = questionService.findAnswerByIds(ids);
        Map<Long, Integer> idToFactScore = new HashMap<>(ids.size());

        int totalCount = 0;
        for (Map.Entry<Long, Answer> idAndAnswer : request.getIdToAnswer().entrySet()) {
            Long questionId = idAndAnswer.getKey();
            Answer factAnswer = idAndAnswer.getValue();
            Answer rightAnswer = idToRightAnswer.get(questionId);
            if (rightAnswer != null) {
                // TODO 可能不存在？
                Integer score = idToScore.get(questionId);
                factAnswer.setTypeId(rightAnswer.getTypeId());
                if (rightAnswer.check(factAnswer)) {
                    totalCount += score;
                    idToFactScore.put(questionId, score);
                } else {
                    idToFactScore.put(questionId, 0);
                }
            }
        }

        //TODO 校验
        ExamRecordEntity entity = request.toEntity(userInfo);
        entity.setFactScore(totalCount);
        entity.setFactScoreDetailJson(JsonUtils.toJson(idToFactScore));
        entity.setReleaseStatusId(ReleaseStatus.SUBMITTED.getId());
        return recordMapper.updateByExamPlanIdAndStudentIdSelective(entity);
    }


    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Integer judge(CheckRequest request,
                         CoreUserInfo userInfo) {
        //TODO 校验
        ExamRecordEntity entity = request.toEntity(userInfo);

        return recordMapper.updateByExamPlanIdAndStudentIdSelective(entity);
    }

    @TargetDataSource(EXAM_READ_ONLY)
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Integer> getStats(Long planId) {

        return recordMapper.selectStats(planId);
    }

}