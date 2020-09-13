package cn.edu.jlu.examsystem.biz.service;

import cn.edu.jlu.examsystem.biz.domain.dto.IdAndName;
import cn.edu.jlu.examsystem.biz.domain.question.QuestionOfChoice;
import cn.edu.jlu.examsystem.biz.domain.question.answer.AnswerOfSingleOption;
import cn.edu.jlu.examsystem.biz.domain.question.description.DescriptionOfChoice;
import cn.edu.jlu.examsystem.biz.domain.dto.request.QuestionCreateRequest;
import cn.edu.jlu.examsystem.biz.domain.dto.response.CoreUserInfo;
import cn.edu.jlu.examsystem.biz.enums.QuestionTypeEnum;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import cn.edu.jlu.examsystem.database.entity.QuestionEntity;
import cn.edu.jlu.examsystem.database.repository.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author WangZeying 2020/9/9 20:43
 */
@SpringBootTest
@ActiveProfiles("test")
class QuestionServiceTest {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionRepository questionRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createQuestion() {
        QuestionCreateRequest request = new QuestionCreateRequest();
        request.setSubjectId(1L);
        request.setSubjectName("测试科目");

        QuestionOfChoice question = new QuestionOfChoice();

        question.setTypeId(QuestionTypeEnum.CHOICE.getId());
        AnswerOfSingleOption answerOfSingleOption = new AnswerOfSingleOption((short) 1);
        question.setAnswer(answerOfSingleOption);
        List<IdAndName<Short>> options = Arrays.asList(
                new IdAndName<>((short) 1, "aaaaa"),
                new IdAndName<>((short) 2, "bbbbb")
        );
        DescriptionOfChoice description = new DescriptionOfChoice(options);
        question.setDescription(description);
        request.setQuestion(question);
        CoreUserInfo userInfo = CoreUserInfo.builder()
                .id(666L)
                .roleId((short) 3)
                .nickname("测试用户")
                .username("test")
                .build();

        System.out.println(JsonUtils.toJson(request));

        Long questionId = questionService.createQuestion(request, userInfo);

        long count = questionRepository.count();
        assertEquals(1, count);

        Optional<QuestionEntity> optional = questionRepository.findById(questionId);
        assertTrue(optional.isPresent());
        System.out.println(JsonUtils.toJson(optional.get()));
    }

}