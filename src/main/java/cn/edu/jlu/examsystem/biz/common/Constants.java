package cn.edu.jlu.examsystem.biz.common;

import cn.edu.jlu.examsystem.biz.domain.paper.QuestionView;
import cn.edu.jlu.examsystem.biz.domain.question.answer.Answer;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

/**
 * @author WangZeying 2020/9/5 20:44
 */

public class Constants {
    
    private Constants() {
    }
    
    public static final TypeReference<List<QuestionView>> QUESTION_VIEW_TYPE = new TypeReference<List<QuestionView>>() {
    };
    
    public static final TypeReference<Map<Long, ? extends Answer>> ID_TO_ANSWER_MAP_TYPE = new TypeReference<Map<Long, ? extends Answer>>() {
    };
}
