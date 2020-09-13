package cn.edu.jlu.examsystem.biz.domain.dto;

import cn.edu.jlu.examsystem.biz.domain.question.Question;
import cn.edu.jlu.examsystem.biz.domain.question.QuestionOfChoice;
import cn.edu.jlu.examsystem.biz.domain.question.answer.AnswerOfSingleOption;
import cn.edu.jlu.examsystem.biz.domain.question.description.DescriptionOfChoice;
import cn.edu.jlu.examsystem.biz.enums.QuestionTypeEnum;
import cn.edu.jlu.examsystem.common.util.JsonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author WangZeying 2020/9/8 0:43
 */
class QuestionTest {

    @BeforeAll
    static void setUp(){
        questionRaw = new QuestionOfChoice();
    }

    static Question<?, ?> questionRaw;

    @Test
    void toJson() {
        QuestionOfChoice question = (QuestionOfChoice) questionRaw;
        AnswerOfSingleOption answerOfSingleOption = new AnswerOfSingleOption((short) 1);
        question.setAnswer(answerOfSingleOption);
        question.setTypeId(QuestionTypeEnum.CHOICE.getId());
        List<IdAndName<Short>> options = Arrays.asList(
                new IdAndName<>((short) 1, "aaaaa"),
                new IdAndName<>((short) 2, "bbbbb")
        );
        DescriptionOfChoice description = new DescriptionOfChoice(options);
        question.setDescription(description);
        String json = JsonUtils.toJson(question);
        System.out.println(json);
        QuestionOfChoice questionDumped = (QuestionOfChoice) JsonUtils.fromJson(json, Question.class);
        assertEquals(json, JsonUtils.toJson(questionDumped));
        assertEquals(question, questionDumped);
    }


    @Test
    void fromJson(){
        String json ="{\"typeId\":1,\"description\":{\"options\":[{\"id\":1,\"name\":\"aaaaa\"},{\"id\":2,\"name\":\"bbbbb\"}]},\"answer\":{\"optionId\":1,\"answer\":1}}";
        QuestionOfChoice questionDumped = (QuestionOfChoice) JsonUtils.fromJson(json, Question.class);
        String jsonDumped = JsonUtils.toJson(questionDumped);
        System.out.println(jsonDumped);
        assertEquals(json,jsonDumped);
    }
}