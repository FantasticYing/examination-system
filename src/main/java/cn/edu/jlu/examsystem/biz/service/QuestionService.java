package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.BatchGet;
import cn.edu.jlu.examsystem.biz.domain.dto.Pagination;
import cn.edu.jlu.examsystem.biz.domain.dto.request.QuestionCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.request.QuestionQueryRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.domain.dto.response.DescriptionVO;
import cn.edu.jlu.examsystem.biz.domain.dto.response.QuestionVO;
import cn.edu.jlu.examsystem.biz.excepiton.BaseException;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.annotation.TargetDataSource;
import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import cn.edu.jlu.examsystem.database.mapper.QuestionMapper;
import cn.edu.jlu.examsystem.database.repository.QuestionRepository;
import cn.edu.jlu.examsystem.database.repository.SubjectRepository;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_ONLY;
import static cn.edu.jlu.examsystem.database.common.DatabaseConstants.EXAM_READ_WRITE;

/**
 * @author WangZeying 2020/9/8 23:51
 */
@Service
public class QuestionService {
    private final SubjectRepository subjectRepository;
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    public QuestionService(SubjectRepository subjectRepository, QuestionRepository questionRepository, QuestionMapper questionMapper) {
        this.subjectRepository = subjectRepository;
        this.questionRepository = questionRepository;
        this.questionMapper = questionMapper;
    }

    @TargetDataSource(EXAM_READ_WRITE)
    @Transactional(rollbackFor = Exception.class)
    public Long createQuestion(QuestionCreateRequest request, CoreUserInfo userInfo) {
        QuestionEntity entity = QuestionEntity.builder()
                .subjectId(request.getSubjectId())
                .creatorId(userInfo.getId())
                .creatorName(userInfo.getNickname())
                .subjectName(
                        Optional.ofNullable(request.getSubjectName())
                                .filter(cs -> !StringUtils.isBlank(cs))
                                .orElseGet(
                                        () -> subjectRepository.findById(request.getSubjectId())
                                                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "科目id不存在 id=" + request.getSubjectId()))
                                                .getName()
                                )
                )
                .typeId(request.getQuestion().getTypeId())
                .descriptionJson(JsonUtils.toJson(request.getQuestion().getDescription()))
                .answerJson(JsonUtils.toJson(request.getQuestion().getAnswer()))
                .build();
        return questionRepository.saveAndFlush(entity).getId();
    }

    @TargetDataSource(EXAM_READ_ONLY)
    public QuestionVO findById(Long id) {
        QuestionEntity entity = questionRepository.findById(id)
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST.value(), "题目问题不存在 id=" + id));
        QuestionVO vo = QuestionVO.builder()
                .description(entity.getDescriptionJson())
                .answer(entity.getAnswerJson())
                .build();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public Map<Long, DescriptionVO> findDescriptionByIds(List<Long> ids) {
        List<QuestionEntity> questionEntities = questionRepository.findAllById(ids);
        return ConvertUtils.extractMap(questionEntities,
                QuestionEntity::getId,
                question -> DescriptionVO.builder()
                        .id(question.getId())
                        .typeId(question.getTypeId())
                        .description(question.getDescriptionJson())
                        .build());
    }

    public BatchGet<QuestionVO> query(QuestionQueryRequest request) {
        QuestionMapper.QueryCondition query = new QuestionMapper.QueryCondition();
        BeanUtils.copyProperties(request, query);

        PageHelper.startPage(request.getPageNum(), request.getPerPage());
        List<QuestionEntity> entities = questionMapper.query(query);

        PageInfo<QuestionEntity> page = PageInfo.of(entities);

        List<QuestionVO> voList = ConvertUtils.extractList(
                entities,
                e -> {
                    QuestionVO vo = QuestionVO.builder()
                            .description(e.getDescriptionJson())
                            .answer(e.getAnswerJson())
                            .build();
                    BeanUtils.copyProperties(e, vo);
                    return vo;
                });

        return BatchGet.of(voList, Pagination.fromPage(page));
    }
}
