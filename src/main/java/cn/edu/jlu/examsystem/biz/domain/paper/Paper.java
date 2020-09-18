package cn.edu.jlu.examsystem.biz.domain.paper;

import cn.edu.jlu.examsystem.biz.domain.dto.response.ExamRecord;
import cn.edu.jlu.examsystem.common.util.ConvertUtils;
import cn.edu.jlu.examsystem.database.entity.PaperEntity;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

/**
 * @author WangZeying 2020/9/17 16:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paper {
    
    private Long id;
    
    private Long subjectId;
    
    private String subjectName;
    
    private String title;
    
    private Long teacherId;
    
    private String teacherName;
    
    private Integer score;
    
    private Integer duration;
    
    @JsonRawValue
    private String questions;
    
    @Nullable
    private ExamRecord examRecord;
    
    public static Paper fromEntity(PaperEntity entity) {
        Paper paper = new Paper();
        BeanUtils.copyProperties(entity, paper);
        paper.setQuestions(entity.getContextJson());
        ExamRecord examRecord = ExamRecord.from(entity.getExamRecordEntity());
        paper.setExamRecord(examRecord);
        return paper;
    }

    public static List<Paper> fromEntity(Collection<PaperEntity> entities) {
        return ConvertUtils.extractList(entities, Paper::fromEntity);
    }
    
}
